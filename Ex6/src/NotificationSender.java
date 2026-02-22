/**
 * Base contract for sending notifications.
 *
 * Contract:
 * - Subclasses must override `validate` to check channel-specific preconditions (e.g., checking if a phone number exists or starts with a required prefix). If validation fails, it should throw an IllegalArgumentException.
 * - Subclasses must override `doSend` to perform the actual sending logic. This is guaranteed to only be called if `validate` succeeds.
 * - Senders will extract and use only the fields from the Notification that are relevant to their specific transport mechanism (e.g., SMS ignores the subject).
 */
public abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    public final void send(Notification n) {
        validate(n);
        doSend(n);
    }

    protected abstract void validate(Notification n);
    protected abstract void doSend(Notification n);
}
