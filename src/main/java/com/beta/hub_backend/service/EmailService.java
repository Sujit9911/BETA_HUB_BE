package com.beta.hub_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private final RestClient restClient;

    @Value("${brevo.api-key}")
    private String apiKey;

    @Value("${brevo.sender-email}")
    private String senderEmail;

    @Value("${brevo.sender-name:BETA Hub}")
    private String senderName;

    public EmailService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.brevo.com")
                .build();
    }

    public void sendAlertEmail(String to, String subject, String description) {

        String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>BETA Hub Alert</title>
                </head>

                <body style="margin:0; padding:0; background-color:#f1f5f9; font-family:Arial, Helvetica, sans-serif;">

                    <div style="width:100%; padding:40px 15px; box-sizing:border-box;">

                        <div style="max-width:650px; margin:0 auto; background:#ffffff; border:1px solid #e2e8f0; border-radius:14px; overflow:hidden;">

                            <div style="background:#1d4ed8; padding:28px 35px;">

                                <div style="font-size:13px; color:#dbeafe; font-weight:bold; letter-spacing:1px; text-transform:uppercase;">
                                    BETA Digital Hub
                                </div>

                                <div style="font-size:25px; color:#ffffff; font-weight:bold; margin-top:8px;">
                                    Important Alert
                                </div>

                                <div style="font-size:13px; color:#bfdbfe; margin-top:6px;">
                                    Official communication from BETA Hub
                                </div>

                            </div>

                            <div style="padding:35px;">

                                <div style="font-size:12px; color:#2563eb; font-weight:bold; text-transform:uppercase; letter-spacing:1px; margin-bottom:10px;">
                                    {{TYPE}}
                                </div>

                                <h2 style="margin:0 0 18px 0; color:#0f172a; font-size:22px; line-height:1.4;">
                                    {{SUBJECT}}
                                </h2>

                                <div style="background:#f8fafc; border-left:4px solid #2563eb; padding:18px 20px; border-radius:6px; margin-bottom:25px;">

                                    <p style="margin:0; color:#475569; font-size:15px; line-height:1.7;">
                                        {{DESCRIPTION}}
                                    </p>

                                </div>

                                <div style="border-top:1px solid #e2e8f0; padding-top:22px;">

                                    <p style="margin:0; color:#64748b; font-size:13px; line-height:1.6;">
                                        Please take note of this notification and take the necessary action, if applicable.
                                    </p>

                                    <p style="margin:15px 0 0 0; color:#64748b; font-size:13px;">
                                        Regards,<br>
                                        <strong style="color:#0f172a;">BETA Hub Team</strong>
                                    </p>

                                </div>

                            </div>

                            <div style="background:#f8fafc; border-top:1px solid #e2e8f0; padding:20px 35px;">

                                <p style="margin:0; color:#94a3b8; font-size:11px; line-height:1.6; text-align:center;">
                                    This is an automated communication from BETA Digital Hub.<br>
                                    Please do not reply directly to this email.
                                </p>

                            </div>

                        </div>

                    </div>

                </body>
                </html>
                """
                .replace("{{TYPE}}", escapeHtml("Official Alert"))
                .replace("{{SUBJECT}}", escapeHtml(subject))
                .replace("{{DESCRIPTION}}", escapeHtml(description));

        Map<String, Object> body = Map.of(
                "sender", Map.of(
                        "name", senderName,
                        "email", senderEmail
                ),
                "to", List.of(
                        Map.of(
                                "email", to
                        )
                ),
                "subject", "BETA Alert: " + subject,
                "htmlContent", htmlContent,
                "textContent", subject + "\n\n" + description
        );

        try {

            Map<?, ?> response = restClient.post()
                    .uri("/v3/smtp/email")
                    .header("api-key", apiKey)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            System.out.println("[Brevo] Email sent to: " + to);

            if (response != null && response.get("messageId") != null) {
                System.out.println("[Brevo] Message ID: " + response.get("messageId"));
            }

        } catch (Exception e) {

            System.err.println("[Brevo] Email failed for: " + to);
            System.err.println("[Brevo] Error: " + e.getMessage());

            throw new RuntimeException(
                    "Brevo email sending failed",
                    e
            );
        }
    }

    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}