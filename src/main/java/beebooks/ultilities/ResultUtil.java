package beebooks.ultilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultUtil {
    String result;
    String message;

    public ResultUtil() {}

    public ResultUtil(String result, String message) {
        this.result = result;
        this.message = message;
    }
}
