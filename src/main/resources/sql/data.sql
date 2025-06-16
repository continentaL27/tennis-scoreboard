-- Insert sample players
INSERT INTO Players (Name) VALUES
('Alice'),
('Bob'),
('Charlie'),
('Diana'),
('Eve');

-- Insert sample matches
-- Note: Assuming IDs start from 1 in the order players were inserted
INSERT INTO Matches (Player1, Player2, Winner) VALUES
(1, 2, 1),    -- Alice vs Bob, Alice won
(3, 4, 4),    -- Charlie vs Diana, Diana won
(1, 3, 1),    -- Alice vs Charlie, Alice won
(2, 4, 2),    -- Bob vs Diana, Bob won
(1, 5, 5),    -- Alice vs Eve, Eve won
(3, 5, 3);    -- Charlie vs Eve, Charlie won