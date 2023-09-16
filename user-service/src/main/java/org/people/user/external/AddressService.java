package org.people.user.external;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.people.user.config.FeignConfig;
import org.people.user.exception.CustomException;
import org.people.user.external.model.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(value = "address-service", url = "${external.service.address.url}", configuration = FeignConfig.class)
public interface AddressService {

    @GetMapping("/address/user/{uuid}")
    ResponseEntity<AddressDto> getAddressByUuid(@PathVariable String uuid);

    default ResponseEntity<Void> fallback(Exception e) {
        throw new CustomException("Address Service is not available",
                "UNAVAILABLE",
                500);
    }
}
