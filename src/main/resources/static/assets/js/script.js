function submitForm() {
  const name = document.getElementById('name').value;
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;

  // Here, you can send the updated profile details to the server using AJAX or fetch().

  alert(`Profile updated successfully!\nName: ${name}\nEmail: ${email}`);
}

// Change the profile image when the user selects a new file
const profileImageUpload = document.getElementById('profile-image-upload');
const profileImage = document.querySelector('.profile-image img');

profileImageUpload.addEventListener('change', (event) => {
  const file = event.target.files[0];

  // Check that the file is actually an image
  if (file && file.type.startsWith('image/')) {
    const reader = new FileReader();

    reader.addEventListener('load', (event) => {
      profileImage.src = event.target.result;
    });

    reader.readAsDataURL(file);
  }
});