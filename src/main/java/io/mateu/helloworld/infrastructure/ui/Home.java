package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.Text;
import io.mateu.uidl.annotations.UI;

@UI("")
public class Home {

    @Text
    String message = "Hello World!";

}
