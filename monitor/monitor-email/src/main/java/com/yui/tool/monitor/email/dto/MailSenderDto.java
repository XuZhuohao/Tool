package com.yui.tool.monitor.email.dto;

import com.sun.mail.iap.Protocol;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author XuZhuohao
 * @date 2019/8/1
 */
@Data
@Accessors(chain = true)
public class MailSenderDto {
    private String host;
    /// 不需要初始化
/*    private String password;
    private String username;*/
    /**
     * 编码
     */
    private String encoding;
    private int port;
    /**
     * ISA/TMG
     */
    private String protocol = "ISA/TMG";
}
