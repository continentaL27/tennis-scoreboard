-- Create Players table
CREATE TABLE Players (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

-- Create unique index on Player name for efficient lookup and to prevent duplicate names
CREATE UNIQUE INDEX idx_player_name ON Players(Name);

-- Create Matches table
CREATE TABLE Matches (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Player1 INT NOT NULL,
    Player2 INT NOT NULL,
    Winner INT NOT NULL,
    FOREIGN KEY (Player1) REFERENCES Players(ID),
    FOREIGN KEY (Player2) REFERENCES Players(ID),
    FOREIGN KEY (Winner) REFERENCES Players(ID),
    CONSTRAINT different_players CHECK (Player1 <> Player2)
);

-- Optional: Create an index for faster match history queries
CREATE INDEX idx_match_players ON Matches(Player1, Player2);