const url = window.location.href;

const homeAnchor = document.getElementById("home-anchor");
const productsAnchor = document.getElementById("products-anchor");
const loginAnchor = document.getElementById("login-anchor");
if(url.includes("home")) {

    homeAnchor.style.fontWeight="800";
    productsAnchor.style.fontWeight="regular";
    loginAnchor.style.fontWeight="regular";
} else if(url.includes("products")) {
    homeAnchor.style.fontWeight="regular";
    productsAnchor.style.fontWeight="800";
    loginAnchor.style.fontWeight="regular";
} else if(url.includes("login")) {
    homeAnchor.style.fontWeight="regular";
    productsAnchor.style.fontWeight="regular";
    loginAnchor.style.fontWeight="800";
}