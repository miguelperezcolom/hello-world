package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.HomeRoute;
import io.mateu.uidl.annotations.Menu;
import io.mateu.uidl.annotations.Subtitle;
import io.mateu.uidl.annotations.Title;
import io.mateu.uidl.annotations.UI;
import io.mateu.uidl.fluent.AppVariant;

/**
 * The application shell — the outer frame around every screen. It stays purely
 * declarative: the same screens can be re-framed by changing annotations alone.
 *
 * <ul>
 *   <li>{@code @App} picks the navigation layout ({@link AppVariant#MENU_ON_LEFT}
 *       — a collapsible sidebar) and enables the light/dark {@code themeToggle};
 *   <li>{@code @Title} / {@code @Subtitle} brand the shell header;
 *   <li>{@code @Menu} fields are the entries; {@code @HomeRoute} the landing route.
 * </ul>
 */
@UI("")
@io.mateu.uidl.annotations.App(value = AppVariant.MENU_ON_LEFT, themeToggle = true)
@Title("Hello World")
@Subtitle("A Mateu tutorial")
@HomeRoute("/home")
public class App {

    @Menu
    Home home;

    @Menu
    ProductCrud products;

    @Menu
    CategoryCrud categories;

    @Menu
    NewProductForm newProduct;

}
