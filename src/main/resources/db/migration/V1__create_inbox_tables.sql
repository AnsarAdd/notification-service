CREATE TABLE email_inbox (
                             id         UUID PRIMARY KEY,
                             created_at TIMESTAMP NOT NULL DEFAULT now(),
                             topic      VARCHAR(100) NOT NULL,
                             key        VARCHAR(255) NOT NULL,
                             value      TEXT NOT NULL,
                             processed  BOOLEAN NOT NULL DEFAULT false,
                             attempt    INTEGER NOT NULL DEFAULT 1
);


CREATE UNIQUE INDEX uk_email_inbox_key_value ON email_inbox (key, value);


CREATE INDEX idx_email_inbox_processed ON email_inbox (processed)
    WHERE processed = false;


CREATE TABLE push_inbox (
                            id         UUID PRIMARY KEY,
                            created_at TIMESTAMP NOT NULL DEFAULT now(),
                            topic      VARCHAR(100) NOT NULL,
                            key        VARCHAR(255) NOT NULL,
                            value      TEXT NOT NULL,
                            processed  BOOLEAN NOT NULL DEFAULT false,
                            attempt    INTEGER NOT NULL DEFAULT 1
);

CREATE UNIQUE INDEX uk_push_inbox_key_value ON push_inbox (key, value);
CREATE INDEX idx_push_inbox_processed ON push_inbox (processed)
    WHERE processed = false;


CREATE TABLE sms_inbox (
                           id         UUID PRIMARY KEY,
                           created_at TIMESTAMP NOT NULL DEFAULT now(),
                           topic      VARCHAR(100) NOT NULL,
                           key        VARCHAR(255) NOT NULL,
                           value      TEXT NOT NULL,
                           processed  BOOLEAN NOT NULL DEFAULT false,
                           attempt    INTEGER NOT NULL DEFAULT 1
);

CREATE UNIQUE INDEX uk_sms_inbox_key_value ON sms_inbox (key, value);
CREATE INDEX idx_sms_inbox_processed ON sms_inbox (processed)
    WHERE processed = false;


CREATE TABLE telegram_inbox (
                                id         UUID PRIMARY KEY,
                                created_at TIMESTAMP NOT NULL DEFAULT now(),
                                topic      VARCHAR(100) NOT NULL,
                                key        VARCHAR(255) NOT NULL,
                                value      TEXT NOT NULL,
                                processed  BOOLEAN NOT NULL DEFAULT false,
                                attempt    INTEGER NOT NULL DEFAULT 1
);

CREATE UNIQUE INDEX uk_telegram_inbox_key_value ON telegram_inbox (key, value);
CREATE INDEX idx_telegram_inbox_processed ON telegram_inbox (processed)
    WHERE processed = false;