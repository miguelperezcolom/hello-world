package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.HomeRoute;
import io.mateu.uidl.annotations.Menu;
import io.mateu.uidl.annotations.UI;

@UI("")
@HomeRoute("/home")
public class App {

    @Menu
    ProductCrud products;

    @Menu
    CategoryCrud categories;

    @Menu
    NewProductForm newProduct;

}
