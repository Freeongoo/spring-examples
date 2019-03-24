package com.example.getbean.service.b;

import org.springframework.stereotype.Service;

@Service("servicePackageB")
public class SomeService {
    public String getHi() {
        return "hi from service package b";
    }
}
