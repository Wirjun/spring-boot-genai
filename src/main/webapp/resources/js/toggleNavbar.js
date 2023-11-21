<!-- javascript to toggle navbar -->
function toggleNavbar() {
    var navbar = document.getElementById("navbar");
    var mainContent = document.getElementsByClassName("main-content")[0];
    if (navbar.style.width === "14rem") {
        navbar.style.width = "3rem";
        mainContent.style.marginLeft = "3rem";
    } else {
        navbar.style.width = "14rem";
        mainContent.style.marginLeft = "14rem";
    }
}