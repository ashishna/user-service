package pro.codeschool.userservice.utils

import java.time.LocalDateTime
import java.time.ZoneId

class DateUtils {

    static Long now() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
    }

    //TODO Fix this
    static Long tomorrow() {
        return LocalDateTime.now().plusDays(24).atZone(ZoneId.systemDefault()).toEpochSecond()
    }

    static Long yesterday() {
        return LocalDateTime.now().minusHours(24).atZone(ZoneId.systemDefault()).toEpochSecond()
    }
}
