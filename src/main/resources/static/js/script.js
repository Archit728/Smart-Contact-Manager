document.addEventListener("DOMContentLoaded", () => {
  console.log("Script loaded");

  // Initialize the current theme
  let currentTheme = localStorage.getItem("theme") || "light";

  // Immediately update the button text and icon
  updateButton(currentTheme);

  // Add event listener to the theme toggle button
  document
    .querySelector("#theme_change_button")
    .addEventListener("click", toggleTheme);

  // Function to toggle the theme between dark and light
  function toggleTheme() {
    const newTheme = currentTheme === "dark" ? "light" : "dark";
    applyTheme(newTheme);
    setTheme(newTheme);
  }

  // Function to apply a theme to the webpage
  function applyTheme(theme) {
    document.documentElement.classList.remove(currentTheme);
    document.documentElement.classList.add(theme);
    currentTheme = theme;
    updateButton(theme); // Update the button text and icon when theme is applied
  }

  // Function to update the button text and icon
  function updateButton(theme) {
    const button = document.querySelector("#theme_change_button");
    const icon = button.querySelector("i");
    const textSpan = button.querySelector("span");

    // Update button text
    textSpan.textContent = theme === "light" ? "Dark" : "Light";

    // Update button icon
    if (theme === "dark") {
      icon.classList.remove("fa-moon");
      icon.classList.add("fa-sun");
      icon.classList.remove("fa-solid");
      icon.classList.add("fa-regular");
    } else {
      icon.classList.remove("fa-sun");
      icon.classList.add("fa-moon");
      icon.classList.remove("fa-regular");
      icon.classList.add("fa-solid");
    }
  }

  // Function to store the theme in localStorage
  function setTheme(theme) {
    localStorage.setItem("theme", theme);
  }
});
