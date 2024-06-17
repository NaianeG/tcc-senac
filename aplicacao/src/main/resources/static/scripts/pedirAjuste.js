function toggleMenu() {    
    const menuMobile = document.getElementById("menu-mobile")

    if(menuMobile.className === "menu-mobile-active") {
        menuMobile.className = "menu-mobile"
    } else {
        menuMobile.className = "menu-mobile-active"
    }
}

function logout(){
    window.location.replace("http://localhost:8080/logout");
}
