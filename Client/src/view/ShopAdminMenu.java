package view;

class ShopAdminMenu extends Show {
    private static ShopAdminMenu menu;

    public static ShopAdminMenu getInstance() {
        if (menu == null) {
            menu = new ShopAdminMenu();
        }
        return menu;
    }

    private ShopAdminMenu() {

    }
}
