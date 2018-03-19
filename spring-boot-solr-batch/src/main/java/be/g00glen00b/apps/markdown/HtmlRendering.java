package be.g00glen00b.apps.markdown;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

@AllArgsConstructor
@Data
public class HtmlRendering {
    private static final String HTML4_UTF_META = "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">";
    private static final String HTML5_UTF_META = "<meta charset=\"UTF-8\">";
    private static final String HEAD = "<!DOCTYPE html>\n<html>\n<head>\n" + HTML4_UTF_META + "\n" + HTML5_UTF_META + "\n</head><body>\n";
    private static final String FOOT = "</body>\n</html>";
    private Resource resource;
    private String html;

    public String getFullHtml() {
        return StringUtils.join(HEAD, getHtml(), FOOT);
    }
}
