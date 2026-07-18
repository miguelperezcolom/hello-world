package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.Route;
import io.mateu.uidl.annotations.Text;

@Route("/home")
public class Home {

    @Text
    String message = "Hello World!";

}
