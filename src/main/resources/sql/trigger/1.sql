-- ----------------------------
-- Triggers structure for table gz_user
-- ----------------------------
DROP TRIGGER IF EXISTS `gz_user_insert_trigger`;;
CREATE TRIGGER `gz_user_insert_trigger` AFTER INSERT ON `gz_user` FOR EACH ROW BEGIN
		declare num int(4);
		set num = (select count(*) from gz_user_modified where user_id=new.user_id and modified_type = 'ADD');
		IF num = 0 THEN
		INSERT INTO gz_user_modified(USER_ID, MODIFIED_TYPE) values (new.user_id,'ADD');
		END IF;
END
;;
-- ----------------------------
-- Triggers structure for table gz_user
-- ----------------------------
DROP TRIGGER IF EXISTS `gz_user_update_trigger`;;
CREATE TRIGGER `gz_user_update_trigger` AFTER UPDATE ON `gz_user` FOR EACH ROW BEGIN
		declare num int(4);
		set num = (select count(*) from gz_user_modified where user_id=new.user_id and modified_type = 'MODIFIED');
		IF num = 0 AND (NEW.USER_PASSWORD<>OLD.USER_PASSWORD OR NEW.USER_ID<>OLD.USER_ID OR NEW.USER_EMAIL<>OLD.USER_EMAIL OR NEW.USER_MOBILE<>OLD.USER_MOBILE OR NEW.USER_IDENTITY_NUMBER<>OLD.USER_IDENTITY_NUMBER)
		THEN
		INSERT INTO gz_user_modified(USER_ID, MODIFIED_TYPE) values (new.user_id,'MODIFIED');
		END IF;
END
;;

-- ----------------------------
-- Triggers structure for table gz_user
-- ----------------------------
DROP TRIGGER IF EXISTS `gz_user_delete_trigger`;;
CREATE TRIGGER `gz_user_delete_trigger` AFTER DELETE ON `gz_user` FOR EACH ROW BEGIN
		declare num int(4);
		set num = (select count(*) from gz_user_modified where user_id=old.user_id and modified_type = 'DELETE');
		IF num = 0 THEN
		INSERT INTO gz_user_modified(USER_ID, MODIFIED_TYPE) values (old.user_id,'DELETE');
		END IF;
END
;;
