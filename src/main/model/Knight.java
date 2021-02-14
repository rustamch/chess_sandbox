package model;


// Represents a knight
public class Knight extends Piece {

    private static final char IDENTIFIER = 'N'; // Identifier of king used by external methods to identify a piece


    public Knight(boolean pieceColor,int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }

    //EFFECTS: Moves a Knight to destination square if it there exists a legal move that would move knight there,
    //         returns false otherwise
    @Override
    public boolean makeMove(int destX, int destY) {
        if (Math.abs(destY - posY) == 2 && Math.abs(destX - posX) == 1) {
            return bd.checkDestinationSquare(pieceColor, posX, posY, destX, destY);
        } else if (Math.abs(destY - posY) == 1 && Math.abs(destX - posX) == 2) {
            return bd.checkDestinationSquare(pieceColor, posX, posY, destX, destY);
        } else {
            return false;
        }
    }

    //EFFECTS: Returns identifier of the Knight
    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }

}