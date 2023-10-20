package cap.backend.back.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ancestor")
@RequiredArgsConstructor
@Slf4j
public class AncestorController {
    @GetMapping("/{id}")
    public ResponseEntity ancestors(@PathVariable Long id, @RequestParam(defaultValue = "real") String type){

        log.info("id={}, type={}", id, type);
        /*
        if(type.equals("real")){
            ;
        }
        else if(type.equals("false")){
            ;
        }
        else
         */
        return ResponseEntity.noContent().build();

    }


}
