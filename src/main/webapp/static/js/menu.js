const openedWidth = '300px';
const closedWidth = '0px';
const openedLeft = '0px';
const closedLeft = '-300px';
const menu = $('#menu');
const menuHelper = $('#menu-helper');
const menuButton = $('#menu-button');

function updateMenuType() {
    if ($(window).width() < BootstrapUtil.breakpoints.lg) {
        menu.css('left', closedLeft);
        menuHelper.css('width', closedWidth);
    } else {
        menu.css('left', openedLeft);
        menuHelper.css('width', openedWidth);
    }
}

function toggleMenu() {
    switch (menu.css('left')) {
        case '0px':
            menu.css('left', closedLeft);
            menuHelper.css('width', closedWidth);
            break;
        case '-300px':
            menu.css('left', openedLeft);
            if ($(window).width() >= BootstrapUtil.breakpoints.lg) {
                menuHelper.css('width', openedWidth);
            }
    
            break;
    }
    console.log();
}

$(window).on('resize', updateMenuType);

menuButton.on('click', toggleMenu);

updateMenuType();