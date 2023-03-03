SELECT * FROM letters;

SELECT letters.id, letters.created, letters.letter FROM letters ORDER BY letters.id DESC LIMIT 1;