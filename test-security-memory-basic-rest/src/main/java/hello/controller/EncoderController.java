package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encode")
public class EncoderController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/{string}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    String get(@PathVariable String string) {
        return passwordEncoder.encode(string);
    }
}
