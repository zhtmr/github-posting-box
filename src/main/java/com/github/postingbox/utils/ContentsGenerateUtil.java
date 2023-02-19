package com.github.postingbox.utils;

import static com.github.postingbox.constants.FileConstant.IMG_DIRECTORY_NAME;

import com.github.postingbox.domain.Board;
import com.github.postingbox.domain.Boards;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ContentsGenerateUtil {

    private static final DateTimeFormatter BOARD_DATE_FORMAT = DateTimeFormatter.ofPattern("yy.MM.dd");
    private static final int COLUMN_SIZE = 3;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String BOARD_INFO_FORMAT = "<td>" + LINE_SEPARATOR
            + "    <a href=\"%s\">" + LINE_SEPARATOR
            + "        <img width=\"100%%\" src=\"%s\"/><br/>" + LINE_SEPARATOR
            + "        <div>%s</div>" + LINE_SEPARATOR
            + "    </a>" + LINE_SEPARATOR
            + "    <div>%s</div>" + LINE_SEPARATOR
            + "    <div>%s</div>" + LINE_SEPARATOR
            + "</td>";

    private ContentsGenerateUtil() {
    }

    public static String toContents(final String beforeDocs, final Boards boards, final String blogUrl) {
        List<Board> boardList = boards.getValue();
        int size = boardList.size();

        StringBuilder stringBuilder = new StringBuilder().append("<table><tbody>");

        for (int i = 0; i < size; i++) {
            if (i % COLUMN_SIZE == 0) {
                stringBuilder.append("<tr>")
                        .append(LINE_SEPARATOR);
            }

            stringBuilder.append(generateTdTag(blogUrl, boardList.get(i)))
                    .append(LINE_SEPARATOR);

            if (i % COLUMN_SIZE == COLUMN_SIZE - 1 || i == size - 1) {
                stringBuilder.append("</tr>")
                        .append(LINE_SEPARATOR);
            }
        }

        stringBuilder.append("</tbody></table>");

        return beforeDocs + LINE_SEPARATOR + stringBuilder;
    }


    private static String generateTdTag(final String blogUrl, final Board board) {
        return String.format(BOARD_INFO_FORMAT,
                blogUrl + board.getLink(),
                String.format("/%s/%s", IMG_DIRECTORY_NAME, board.getResizedImageName()),
                board.getTitle(),
                StringUtil.removeLink(StringUtil.substringByByte(110, board.getSummary()) + "..."),
                board.getDate().format(BOARD_DATE_FORMAT)
        );
    }
}