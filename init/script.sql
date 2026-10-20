CREATE TABLE IF NOT EXISTS fraud (
    id INT PRIMARY KEY AUTO_INCREMENT,
    step INT,
    type VARCHAR(30),
    amount DECIMAL(20, 2),
    name_orig VARCHAR(100),
    old_balance_orig DECIMAL(20, 2),
    new_balance_orig DECIMAL(20, 2),
    name_dest VARCHAR(100),
    old_balance_dest DECIMAL(20, 2),
    new_balance_dest DECIMAL(20, 2),
    is_fraud BOOLEAN,
    is_flagged_fraud BOOLEAN
);