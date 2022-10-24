-- liquibase formatted sql

-- changeset i.filonova:3
ALTER TABLE cat_pet_report
    ALTER COLUMN text_of_report DROP NOT NULL;

ALTER TABLE cat_pet_report
    ALTER COLUMN photo_link DROP NOT NULL;

ALTER TABLE dog_pet_report
    ALTER COLUMN text_of_report DROP NOT NULL;

ALTER TABLE dog_pet_report
    ALTER COLUMN photo_link DROP NOT NULL;


-- changeset i.filonova:4
ALTER TABLE cat_user
ADD CONSTRAINT cat_user_chat_id_unique UNIQUE (chat_id);

ALTER TABLE dog_user
    ADD CONSTRAINT dog_user_chat_id_unique UNIQUE (chat_id);