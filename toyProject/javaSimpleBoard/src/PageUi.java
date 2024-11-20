public class PageUi{
    private Member member;
    public PageUi(){}
    public PageUi(Member member){
        this.member = member;
    }
    public void mainUi(){
        System.out.println("┌────────────────메인 페이지────────────────ㅁx──┐");
        System.out.println("│                                              │");
        System.out.println("│                 메인페이지임                   │");
        System.out.println("│                                              │");
        System.out.println("│                                              │");
        System.out.println("├──────────────────────────────────────────────┤");
        System.out.println("│                                              │");
        System.out.println("│   login, '로그인'                             │");
        System.out.println("│   registger, '회원가입'                       │");
        System.out.println("│   postlist, '게시물 목록'                      │");
        System.out.println("│   write, '글쓰기'                             │");
        System.out.println("│   exit, '종료'                                │");
        System.out.println("│   help, '도움말'                              │");
        System.out.println("│                                              │");
        System.out.println("└──────────────────────────────────────────────┘");
    }
    public void firstLoadUi(){
        LoadingBar loadingBar = new LoadingBar();
        System.out.println("┌────────────────로딩중────────────────ㅁx──┐");
        System.out.println("│                                          │");
        loadingBar.getLoadingBar();
    }
    public void helpUi(){
        System.out.println("┌────────────────────────도움말────────────────────ㅁx──┐");
        System.out.println("│                                                     │");
        System.out.println("│                        도움말                        │");
        System.out.println("│                                                     │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.println("│                                                     │");
        System.out.println("│   'login' 또는 '로그인' 입력시 로그인창 뜰듯             │");
        System.out.println("│   'register' 또는 '회원가입' 입력시 회원가입창 뜰듯       │");
        System.out.println("│   'postlist' 또는 '게시물 목록' 입력시 게시글들 뜰듯      │");
        System.out.println("│   'write' 또는 '글쓰기' 입력시 글쓰기 뜰듯               │");
        System.out.println("│   'exit' 또는 '종료' 입력시 종료                       │");
        System.out.println("│   'help' 또는 '도움말' 입력시 지금 보고있는 창 뜸         │");
        System.out.println("│                                                     │");
        System.out.println("└─────────────────────────────────────────────────────┘");
    }
    public void afterLoginUi(){
        System.out.println("┌────────────────메인 페이지────────────────ㅁx──┐");
        System.out.println("│                                              │");
        System.out.println("│                 메인페이지임                   │");
        System.out.println("│                                              │");
        System.out.println("│                                              │");
        System.out.println("├──────────────────────────────────────────────┤");
        System.out.println("│                                              │");
        System.out.println("│   postlist, '게시물 목록'                      │");
        System.out.println("│   write, '글쓰기'                             │");
        System.out.println("│   exit, '종료'                                │");
        System.out.println("│   logout, '로그아웃'                           │");
        System.out.println("│                                              │");
        System.out.println(String.format("│  로그인됨 %s                                │",member.getUserName()));
        System.out.println("└──────────────────────────────────────────────┘");
    }
    public void writePostUi(String writeInfo){
        System.out.println("┌────────────────────────글쓰기────────────────────ㅁx──┐");
        System.out.println("│                                                     │");
        System.out.println("│                        글쓰기                        │");
        System.out.println("│                                                     │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.println("│                                                     │");
        System.out.println(String.format("│         %s                                │",writeInfo));
        System.out.println("│                                                     │");
        System.out.println("│   이전                                               │");
        System.out.println("└─────────────────────────────────────────────────────┘");
    }
}
