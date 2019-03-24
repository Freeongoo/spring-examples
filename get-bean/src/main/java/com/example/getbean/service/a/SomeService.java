package com.example.getbean.service.a;

import org.springframework.stereotype.Service;

@Service("servicePackageA")
public class SomeService {
    public String getHi() {
        return "hi from service package a";
    }
}
