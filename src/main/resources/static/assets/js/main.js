(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();
    
    
    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 500, 'easeInOutExpo');
        return false;
    });


    // Sidebar Toggler
    $('.sidebar-toggler').click(function () {
        $('.sidebar, .content').toggleClass("open_sidebar");
        return false;
    });
    $('.close_btn').click(function () {
        $('.sidebar, .content').removeClass("open_sidebar");
        return false;
    });
    
    document.addEventListener('click', function handleClickOutsideBox(event) {
          // 👇️ the element the user clicked
        //   console.log('user clicked: ', box);
          
          const box = document.querySelectorAll('.open_sidebar');
      
        if (box.length>0&&!event.target.classList.contains('nav-link')&&!event.target.classList.contains('navbar')&&!event.target.classList.contains('sidebar')&&!event.target.classList.contains('navbar-nav')) {
          box.forEach(item=>item.classList.remove('open_sidebar'));
        }
           console.log('user clicked: ', event.target);

      });

    // Add slideDown animation to Bootstrap dropdown when expanding
    $('.dropdown').on('show.bs.dropdown', function() {
        $(this).find('.dropdown-menu').first().stop(true, true).slideDown();
    });

    // Add slideUp animation to Bootstrap dropdown when collapsing.
    $('.dropdown').on('hide.bs.dropdown', function() {
        $(this).find('.dropdown-menu').first().stop(true, true).slideUp();
    });
})(jQuery);


const searchInput = document.getElementById('search-input');
        searchInput.addEventListener('input', handleSearchInput);

function handleSearchInput(event) {
  const query = event.target.value;
  const url = `/search?q=${query}`;
  axios.get(url)
    .then(response => {
      const resultsContainer = document.getElementById('search-results');
      resultsContainer.innerHTML = '';
      response.data.forEach(result => {
        const titleElement = document.createElement('h2');
        titleElement.innerText = result.title;
        const descriptionElement = document.createElement('p');
        descriptionElement.innerText = result.description;
        const resultElement = document.createElement('div');
        resultElement.appendChild(titleElement);
        resultElement.appendChild(descriptionElement);
        resultsContainer.appendChild(resultElement);
      });
    })
    .catch(error => {
      console.error('Error searching for results:', error);
    });
}


