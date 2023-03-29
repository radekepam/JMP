package dto;

import org.springframework.stereotype.Component;

@Component
public class ExBean {

    {
        System.out.println("static");
    }
    ExBean() {
        System.out.println("jdksfl");
    }
}
