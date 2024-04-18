CREATE TRIGGER prevent_dimension_update
BEFORE UPDATE ON Risk_Dimension
FOR EACH ROW
BEGIN
  IF NEW.dimension != OLD.dimension THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Updating the "dimension" column is not allowed';
  END IF;
END;