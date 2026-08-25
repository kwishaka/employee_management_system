package com.ems.mis.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    // Change this later when the frontend is deployed
    private static final String FRONTEND_URL = "http://localhost:5173";
    public void sendApplicationConfirmation(
            String applicantEmail,
            String fullName,
            String trackingId) {
        try {
            String trackingLink =
                    FRONTEND_URL + "/track/" + trackingId;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(applicantEmail);
            helper.setSubject(
                    "Employee Management System - Application Received"
            );
            String html = """
                    <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6;">

                        <h2>Application Received</h2>

                        <p>Dear %s,</p>

                        <p>
                            Thank you for submitting your application
                            to the Employee Management System.
                        </p>

                        <p>
                            Your application has been successfully received.
                        </p>

                        <p>
                            <strong>Tracking ID:</strong> %s<br>
                            <strong>Status:</strong> PENDING
                        </p>

                        <p>
                            Please keep your Tracking ID safe.
                        </p>

                        <p>
                            <a href="%s"
                               style="
                               display:inline-block;
                               padding:12px 20px;
                               background:#0d6efd;
                               color:white;
                               text-decoration:none;
                               border-radius:5px;">
                               Track Your Application
                            </a>
                        </p>

                        <p>
                            We will notify you when your application
                            has been reviewed.
                        </p>

                        <p>
                            Kind regards,<br>
                            <strong>Employee Management System HR Team</strong>
                        </p>

                    </body>
                    </html>
                    """.formatted(fullName, trackingId, trackingLink);

            helper.setText(html, true);

            mailSender.send(message);

            log.info(
                    "Confirmation email sent successfully to {}",
                    applicantEmail
            );

        } catch (MessagingException e) {
            log.error(
                    "Failed to send confirmation email to {}: {}",
                    applicantEmail,
                    e.getMessage()
            );
        } catch (Exception e) {
            log.error(
                    "Unexpected error sending confirmation email to {}: {}",
                    applicantEmail,
                    e.getMessage()
            );
        }
    }

    public void sendApplicationStatusEmail(
            String applicantEmail,
            String fullName,
            String trackingId,
            String status,
            String notes) {

        try {
            String trackingLink =
                    FRONTEND_URL + "/track/" + trackingId;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(applicantEmail);
            helper.setSubject(
                    "Employee Management System - Application Status"
            );

            String safeNotes =
                    notes != null && !notes.isBlank()
                            ? notes
                            : "No additional notes.";

            String html = """
                    <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6;">

                        <h2>Application Status</h2>

                        <p>Dear %s,</p>

                        <p>
                            Your application has been reviewed.
                        </p>

                        <p>
                            <strong>Tracking ID:</strong> %s<br>
                            <strong>Status:</strong> %s
                        </p>

                        <p>
                            <strong>HR Notes:</strong><br>
                            %s
                        </p>

                        <p>
                            <a href="%s"
                               style="
                               display:inline-block;
                               padding:12px 20px;
                               background:#0d6efd;
                               color:white;
                               text-decoration:none;
                               border-radius:5px;">
                               Track Your Application
                            </a>
                        </p>

                        <p>
                            You can use the link above to view
                            your current application status.
                        </p>

                        <p>
                            Thank you for using the Employee Management System.
                        </p>

                        <p>
                            Kind regards,<br>
                            <strong>Employee Management System HR Team</strong>
                        </p>

                    </body>
                    </html>
                    """.formatted(
                    fullName,
                    trackingId,
                    status,
                    safeNotes,
                    trackingLink
            );

            helper.setText(html, true);

            mailSender.send(message);

            log.info(
                    "Status email sent successfully to {}",
                    applicantEmail
            );

        } catch (MessagingException e) {
            log.error(
                    "Failed to send status email to {}: {}",
                    applicantEmail,
                    e.getMessage()
            );
        } catch (Exception e) {
            log.error(
                    "Unexpected error sending status email to {}: {}",
                    applicantEmail,
                    e.getMessage()
            );
        }
    }
}