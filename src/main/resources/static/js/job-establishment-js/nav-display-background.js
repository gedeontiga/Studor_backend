window.addEventListener("scroll", function(){
    let header = document.querySelector(".hero_area");
    let fluidContainer = document.querySelector(".header_section .container-fluid")
    let nav = document.querySelector("nav")
    let background = getComputedStyle(header).backgroundImage;
    let navLinkStyle = document.querySelectorAll(".custom_nav-container.navbar-expand-lg .navbar-nav .nav-link ")

    //Check if background is an image
    if(background.includes("url")){
        let imageHeight = header.offsetHeight
        let navHeight = nav.offsetHeight
        let scrollTop = window.scrollY || document.documentElement.scrollTop

        //Check if the nav is above the image
        if(scrollTop < imageHeight - navHeight){
            // alert("hello world")
            navLinkStyle.forEach(function(elmt){
                elmt.style.color = "#210446"
            })
            fluidContainer.style.backgroundColor = "transparent";
            nav.style.backgroundColor = "transparent"
        }
        else{
            // alert("goodbye world")
            console.log(navLinkStyle)
            navLinkStyle.forEach(function(elmt){
                elmt.style.color = "white"
            })
            fluidContainer.style.backgroundColor = "#210446"
            nav.style.backgroundColor = "#210446"
        }
    }
})