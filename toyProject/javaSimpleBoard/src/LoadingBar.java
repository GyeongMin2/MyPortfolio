public class LoadingBar {
    int totalLength = 20;
    void getLoadingBar(){
        char[] loadingBar = new char[totalLength];
        loadingBar[0]='[';
        loadingBar[totalLength-1] = ']';

        for (int i = 1; i < totalLength-1; i++) {
            loadingBar[i] = '-';
        }

        for (int i = 1;i < totalLength-1; i++) {
            System.out.print("\r         " + new String(loadingBar) + "\t 로딩중" + "\r");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            // '-'를 '|'로 변경
            loadingBar[i] = '|';
        }
        System.out.print("\r" + " ".repeat(totalLength)+ "\r");
    }
}
