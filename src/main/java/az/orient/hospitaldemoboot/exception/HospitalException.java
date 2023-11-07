package az.orient.hospitaldemoboot.exception;

public class HospitalException extends RuntimeException {


    private Integer code;

    public HospitalException(Integer code, String message) {
        super(message);
        this.code = code;

    }

    public Integer getCode() {
        //getCode kodu bank exceptions sinifine aiddir. Istenilen gelen kodu sonda geri qaytarir.
        return code;

    }
}

