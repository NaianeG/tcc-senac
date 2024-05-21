function toggleMenu() {
    
    const menuMobile = document.getElementById("menu-mobile")

    if(menuMobile.className === "menu-mobile-active") {
        menuMobile.className = "menu-mobile"
    } else {
        menuMobile.className = "menu-mobile-active"
    }
}

function apresentarMenuDocentes() {
    
    const menuDocentes = document.getElementById("container-direita")

    if (menuDocentes.className === "container-direita"){
        menuDocentes.className = "container-direita-active"
    } else {
        menuDocentes.className = "container-direita"
    }
}