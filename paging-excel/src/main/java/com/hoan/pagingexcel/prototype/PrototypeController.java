package com.hoan.pagingexcel.prototype;


import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.service.PrototypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class PrototypeController {

    private final PrototypeService prototypeService;

    @GetMapping({"", "/"})
    public String redirectToPrototype() {
        return "redirect:/prototype1-1";
    }

    @GetMapping("/prototype1-1")
    public String getPrototype() {
        return "prototype";
    }

    @GetMapping( {"/prototype1-2","/prototype1-3","/prototype1-4"})
    public String getDummyPage() {
        return "dummy";
    }

    @PostMapping("/getPrototypeList")
    public ResponseEntity getPrototypeList(@RequestBody PageVO requestPage) {
        HashMap<String,Object> response = prototypeService.getPrototypeList(requestPage);
        return ResponseEntity.ok(response);
    }


}
