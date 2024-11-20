package net.haebup.dto.board.comment;

import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.qna.QnaDTO;
import net.haebup.dto.qna.qnaComment.QnaCommentDTO;

public class JoinCommentDTO {
	private BoardCommentDTO boardComment;
    private QnaCommentDTO qnaComment;
    private BoardDTO board;
    private QnaDTO qna;

    public JoinCommentDTO(BoardCommentDTO boardComment, QnaCommentDTO qnaComment, BoardDTO board, QnaDTO qna) {
        this.boardComment = boardComment;
        this.qnaComment = qnaComment;
        this.board = board;
        this.qna = qna;
    }

    public BoardCommentDTO getBoardComment() {
        return boardComment;
    }

    public void setBoardComment(BoardCommentDTO boardComment) {
        this.boardComment = boardComment;
    }

    public QnaCommentDTO getQnaComment() {
        return qnaComment;
    }

    public void setQnaComment(QnaCommentDTO qnaComment) {
        this.qnaComment = qnaComment;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    public QnaDTO getQna() {
        return qna;
    }

    public void setQna(QnaDTO qna) {
        this.qna = qna;
    }
    

}
