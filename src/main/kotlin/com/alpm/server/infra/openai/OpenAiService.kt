package com.alpm.server.infra.openai

import com.alpm.server.global.common.model.Language
import com.alpm.server.infra.openai.dto.ChatMessageDto
import com.alpm.server.infra.openai.dto.ChatRequestDto
import com.alpm.server.infra.openai.dto.ChatResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class OpenAiService (

    @Value("\${openai.token}")
    private val token: String,

    @Value("\${openai.url}")
    private val url: String,

    private val restTemplate: RestTemplate

) {

    private val cAnnotatePrompt = arrayListOf(
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\nll Query(ll n, ll l, ll r, ll st, ll ed){\n    if (l>ed || r<st)\n        return 0;\n    if (l>=st && r<=ed)\n        return SegTree[n];\n    ll mid=(l+r)/2;\n    return Query(n*2,l,mid,st,ed)+Query(n*2+1,mid+1,r,st,ed);\n}\nll Update(ll n, ll l, ll r, ll pos, ll x){\n    if (l>pos || r<pos)\n        return SegTree[n];\n    if (l==r)\n        return SegTree[n]=x;\n    ll mid=(l+r)/2;\n    return SegTree[n]=Update(n*2,l,mid,pos,x)+Update(n*2+1,mid+1,r,pos,x);\n}\nll Init(ll n, ll l, ll r){\n    if (l==r)\n        return SegTree[n]=arr[l];\n    ll mid=(l+r)/2;\n    return SegTree[n]=Init(n*2,l,mid)+Init(n*2+1,mid+1,r);\n}\nint main(){\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n    cin >> N >> M;\n    for (int i=1;i<=N;i++)\n        cin >> arr[i];\n    SegTree.resize(4*N);\n    Init(1,1,N);\n    for (int i=0;i<M;i++){\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x)\n            cout << Query(1,1,N,a,b) << \"\\n\";\n        else\n            Update(1,1,N,a,b);\n    }\n    return 0;\n}",
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\n\nll Query(ll n, ll l, ll r, ll st, ll ed) {\n    if (l > ed || r < st) // 탐색 범위를 벗어나는 경우\n        return 0;\n    if (l >= st && r <= ed) // 목표 범위에 속하는 경우\n        return SegTree[n];\n    ll mid = (l + r) / 2; // 중심값 선언\n    return Query(n * 2, l, mid, st, ed) + Query(n * 2 + 1, mid + 1, r, st, ed); // 왼쪽 구간과 오른쪽 구간을 탐색\n} \n\nll Update(ll n, ll l, ll r, ll pos, ll x) {\n    if (l > pos || r < pos) // 탐색범위를 벗어나면\n        return SegTree[n]; // 해당 세그먼트 트리의 값을 반환\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = x;\n        return SegTree[n]; // 새로 입력받은 값을 반환\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Update(n * 2, l, mid, pos, x) + Update(n * 2 + 1, mid + 1, r, pos, x); // 왼쪽 구간과 오른쪽 구간을 탐색\n    return SegTree[n];\n}\n\nll Init(ll n, ll l, ll r) {\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = arr[l]; // arr[l] 값이 상위노드로 넘어감\n        return SegTree[n];\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Init(n * 2, l, mid) + Init(n * 2 + 1, mid + 1, r); // 왼쪽 구간과 오른쪽 구간으로 분할\n    return SegTree[n];\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N >> M;\n    for (int i = 1; i <= N; i++)\n        cin >> arr[i]; // 원소 입력 받음\n\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\n    Init(1, 1, N); // 세그먼트 트리 초기화\n\n    for (int i = 0; i < M; i++) {\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x) // x가 1이면\n            cout << Query(1, 1, N, a, b) << \"\\n\"; // a번째부터 b번째까지의 합 출력\n        else // x가 0이면\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\n    }\n\n    return 0;\n}",
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\nll N;\nll arr[1005];\nvoid SelectionSort(){\n    for (int i=0;i<N;i++){\n        ll mn=i;\n        for (int j=i+1;j<N;j++){\n            if (arr[mn]>arr[j])\n                mn=j;\n        }\n        if (i!=mn){\n            ll tmp=arr[i];\n            arr[i]=arr[mn];\n            arr[mn]=tmp;\n        }\n    }\n}\nint main(){\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n    cin >> N;\n    for (int i=0;i<N;i++)\n        cin >> arr[i];\n    SelectionSort();\n    for (int i=0;i<N;i++)\n        cout << arr[i] << \" \";\n    return 0;\n}",
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N; // 배열의 크기를 나타내는 변수\nll arr[1005]; // 정렬할 배열\n\nvoid SelectionSort() { // 선택 정렬 함수\n    for (int i = 0; i < N; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n        ll mn = i; // 가장 작은 값을 가리키는 인덱스\n        \n        for (int j = i + 1; j < N; j++) { // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\n            if (arr[mn] > arr[j])\n                mn = j;\n        }\n        \n        if (i != mn) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\n            ll tmp = arr[i];\n            arr[i] = arr[mn];\n            arr[mn] = tmp;\n        }\n    }\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N; // 배열의 크기를 입력 받음\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\n        cin >> arr[i];\n\n    \n    SelectionSort(); // 선택 정렬 수행\n\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\n        cout << arr[i] << \" \";\n\n    return 0;\n}",
        "위 예시처럼 주어진 코드를 예쁘게 정렬하고 핵심 내용에 대해 주석을 작성해 줘. 단, 주석은 설명하고자 하는 부분의 오른쪽에 위치해야 해. 부가적인 설명 없이 코드와 주석만 작성해 줘. ```로 감쌀 필요도 없어. "
    )

    private val pythonAnnotatePrompt = arrayListOf(
        "import sys\nsys.setrecursionlimit(10**6)\n\ndef query(n,l,r,st,ed):\n    if l>ed or r<st:\n        return 0\n    if l>=st and r<=ed:\n        return seg_tree[n]\n    mid=(l+r)//2\n    return query(n*2,l,mid,st,ed)+query(n*2+1,mid+1,r,st,ed)\n\ndef update(n,l,r,pos,x):\n    if l>pos or r<pos:\n        return seg_tree[n]\n    if l==r:\n        seg_tree[n]=x\n        return x\n    mid=(l+r)//2\n    seg_tree[n]=update(n*2,l,mid,pos,x)+update(n*2+1,mid+1,r,pos,x)\n    return seg_tree[n]\n\ndef init(n,l,r):\n    if l==r:\n        seg_tree[n]=arr[l]\n        return arr[l]\n    mid=(l+r)//2\n    seg_tree[n]=init(n*2,l,mid)+init(n*2+1,mid+1,r)\n    return seg_tree[n]\n\nN,M=map(int,input().split())\narr=[0]+list(map(int, input().split()))\nseg_tree=[0]*(4*N)\ninit(1,1,N)\nfor _ in range(M):\n    x,a,b=map(int,input().split())\n    if x:\n        print(query(1,1,N,a,b))\n    else:\n        update(1,1,N,a,b)",
        "import sys\nsys.setrecursionlimit(10**6)\n\ndef query(n, l, r, st, ed):\n    if l > ed or r < st: # 범위 밖인 경우 0 반환\n        return 0\n    if l >= st and r <= ed: # 범위 내에 완전히 속한 경우 현재 노드의 값 반환\n        return seg_tree[n]\n    mid = (l + r) // 2 # 중간 지점 계산\n    return query(n * 2, l, mid, st, ed) + query(n * 2 + 1, mid + 1, r, st, ed)\n\ndef update(n, l, r, pos, x):\n    if l > pos or r < pos: # 업데이트 위치가 범위 밖인 경우 현재 노드 값 반환\n        return seg_tree[n]\n    if l == r: # 리프 노드인 경우 값 업데이트\n        seg_tree[n] = x\n        return x\n    mid = (l + r) // 2 # 중간 지점 계산\n    seg_tree[n] = update(n * 2, l, mid, pos, x) + update(n * 2 + 1, mid + 1, r, pos, x)\n    return seg_tree[n]\n\ndef init(n, l, r):\n    if l == r: # 리프 노드인 경우 배열 값으로 초기화\n        seg_tree[n] = arr[l]\n        return arr[l]\n    mid = (l + r) // 2 # 중간 지점 계산\n    seg_tree[n] = init(n * 2, l, mid) + init(n * 2 + 1, mid + 1, r)\n    return seg_tree[n]\n\nN, M = map(int, input().split()) # 배열 크기 N과 쿼리 수 M 입력\narr = [0] + list(map(int, input().split())) # 배열 입력 (1-indexed)\nseg_tree = [0] * (4 * N) # 세그먼트 트리 초기화\ninit(1, 1, N) # 세그먼트 트리 생성\n\nfor _ in range(M):\n    x, a, b = map(int, input().split())\n    if x: # 쿼리 연산\n        print(query(1, 1, N, a, b))\n    else: # 업데이트 연산\n        update(1, 1, N, a, b)",
        "def SelectionSort(arr):\n    for i in range(len(arr)):\n        mn=i\n        for j in range(i+1,len(arr)):\n            if arr[mn] > arr[j]:\n                mn=j\n        if i!=mn:\n            arr[i],arr[mn]=arr[mn],arr[i]\n\nN=int(input())\narr=list(map(int,input().split()))\nSelectionSort(arr)\nprint(*arr)",
        "def SelectionSort(arr):\n    for i in range(len(arr)): # 배열의 각 요소에 대해\n        mn = i # 현재 위치를 최소값 인덱스로 초기화\n        for j in range(i + 1, len(arr)): # 현재 위치 이후의 요소들을 순회\n            if arr[mn] > arr[j]: # 현재 최소값보다 작은 값을 찾으면\n                mn = j # 최소값 인덱스를 업데이트\n        if i != mn: # 최소값 인덱스가 현재 위치와 다르면\n            arr[i], arr[mn] = arr[mn], arr[i] # 두 값을 교환\n\nN = int(input()) # 배열 크기 N 입력\narr = list(map(int, input().split())) # 배열 요소 입력\nSelectionSort(arr) # 선택 정렬 함수 호출\nprint(*arr) # 정렬된 배열 출력\n",
        "위 예시처럼 주어진 코드를 예쁘게 정렬하고 핵심 내용에 대해 주석을 작성해 줘. 단, 주석은 설명하고자 하는 부분의 오른쪽에 위치해야 해. 코드와 주석이 아닌 부가적인 설명은 생략해 줘. ```로 감쌀 필요도 없어. "
    )

    private val javaAnnotatePrompt = arrayListOf(
        "import java.util.*;\n\npublic class SegmentTree{\n    static int N, M;\n    static int[] arr=new int[100005];\n    static ArrayList<Integer> SegTree=new ArrayList<>();\n\n    public static int Query(int n, int l, int r, int st, int ed){\n        if (l>ed || r<st)\n            return 0;\n        if (l>=st && r<=ed)\n            return SegTree.get(n);\n        int mid=(l+r)/2;\n        return Query(n*2,l,mid,st,ed)+Query(n*2+1,mid+1,r,st,ed);\n    }\n\n    public static int Update(int n, int l, int r, int pos, int x){\n        if (l>pos || r<pos)\n            return SegTree.get(n);\n        if (l==r){\n            SegTree.set(n,x);\n            return SegTree.get(n);\n        }\n        int mid=(l+r)/2;\n        SegTree.set(n,Update(n*2,l,mid,pos,x)+Update(n*2+1,mid+1,r,pos,x));\n        return SegTree.get(n);\n    }\n\n    public static int Init(int n, int l, int r){\n        if (l==r){\n            SegTree.set(n,arr[l]);\n            return SegTree.get(n);\n        }\n        int mid=(l+r)/2;\n        SegTree.set(n,Init(n*2,l,mid)+Init(n*2+1,mid+1,r));\n        return SegTree.get(n);\n    }\n\n    public static void main(String[] args){\n        Scanner sc=new Scanner(System.in);\n        N=sc.nextInt();\n        M=sc.nextInt();\n        for (int i=1;i<=N;i++)\n            arr[i]=sc.nextInt();\n        for (int i=0;i<4*N;i++)\n            SegTree.add(0);\n        Init(1,1,N);\n        for (int i=0;i<M;i++){\n            int x=sc.nextInt();\n            int a=sc.nextInt();\n            int b=sc.nextInt();\n            if (x!=0)\n                System.out.println(Query(1,1,N,a,b));\n            else\n                Update(1,1,N,a,b);\n        }\n        sc.close();\n    }\n}",
        "import java.util.*;\n\npublic class SegmentTree {\n\n    static int N, M;  // 배열의 크기 N과 쿼리의 수 M\n    static int[] arr = new int[100005];  // 입력 배열\n    static ArrayList<Integer> SegTree = new ArrayList<>();  // 세그먼트 트리 배열\n\n    public static int Query(int n, int l, int r, int st, int ed) { // 구간 합을 구하는 쿼리 함수\n        if (l > \$ed\$ || r < \$st\$) // 탐색 범위를 벗어나는 경우\n        return \$0\$;\n    if (l >= \$st\$ && r <= \$ed\$) // 목표 범위에 속하는 경우\n        return \$SegTree[n]\$;\n        int mid = (l + r) / 2;  // 중심값 선언\n    return Query(\$n * 2\$, \$l\$, \$mid\$, \$st\$, \$ed\$) + Query(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$st\$, \$ed\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n    }\n\n    public static int Update(int n, int l, int r, int pos, int x) { // 세그먼트 트리 업데이트 함수\n        if (l > \$pos\$ || r < \$pos\$) // 탐색범위를 벗어나면\n        return \$SegTree[n]\$; // 해당 세그먼트 트리의 값을 반환\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$x\$;\n        return \$SegTree[n]\$; // 새로 입력받은 값을 반환\n    }\n        int mid = \$(l + r) / 2\$; // 중심값 선언\n        SegTree.set(n, Update(\$n * 2\$, \$l\$, \$mid\$, \$pos\$, \$x\$) + Update(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$pos\$, \$x\$));  // 왼쪽 구간과 오른쪽 구간을 탐색\n        return \$SegTree.get(n)\$;\n    }\n\n    public static int Init(int n, int l, int r) { // 세그먼트 트리 초기화 함수\n        if (l == \$r\$) {  // 리프 노드인 경우\n            SegTree.set(n, \$arr[l]\$);  // 입력 배열의 값으로 초기화\n            return \$SegTree.get(n)\$;  \n        }\n        int mid = (l + r) / 2;  // 중간 인덱스 계산\n        SegTree.set(n, Init(\$n * 2\$, \$l\$, \$mid\$) + Init(\$n * 2 + 1\$, \$mid + 1\$, \$r\$));  // 좌우 자식을 재귀적으로 호출하여 초기화\n        return \$SegTree.get(n)\$;\n    }\n\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);  // 입력을 받기 위한 스캐너 객체 생성\n        N = sc.nextInt();  // 배열의 크기 입력\n        M = sc.nextInt();  // 쿼리의 수 입력\n        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();  // 배열의 요소 입력\n        for (int i = 0; i < 4 * N; i++) SegTree.add(0);  // 세그먼트 트리 크기가 4*N 인 벡터 생성\n        Init(1, 1, N);  // 세그먼트 트리 초기화\n        for (int i = 0; i < M; i++) {\n            int x = sc.nextInt();  // 쿼리 유형 입력 (0: 업데이트, 1: 구간 합)\n            int a = sc.nextInt();  // 첫 번째 인자 입력\n            int b = sc.nextInt();  // 두 번째 인자 입력\n            if (x != 0) System.out.println(Query(1, 1, N, a, b));  // 구간 합 쿼리 수행 후 출력\n            else Update(1, 1, N, a, b);  // 업데이트 쿼리 수행\n        }\n        sc.close();  // 스캐너 닫기\n    }\n}",
        "import java.util.*;\n\npublic class SelectionSort{\n    static int N;\n    static int[] arr=new int[1005];\n\n    public static void SelectionSort(){\n        for (int i=0;i<N;i++){\n            int mn=i;\n            for (int j=i+1;j<N;j++){\n                if (arr[mn]>arr[j])\n                    mn=j;\n            }\n            if (i!=mn){\n                int tmp=arr[i];\n                arr[i]=arr[mn];\n                arr[mn]=tmp;\n            }\n        }\n    }\n\n    public static void main(String[] args){\n        Scanner sc=new Scanner(System.in);\n        N=sc.nextInt();\n        for (int i=0;i<N;i++)\n            arr[i]=sc.nextInt();\n        SelectionSort();\n        for (int i=0;i<N;i++)\n            System.out.print(arr[i]+\" \");\n        sc.close();\n    }\n}",
        "import java.util.*;\n\npublic class SelectionSort {\n    static int N;  // 배열의 크기\n    static int[] arr = new int[1005];  // 정렬할 배열\n\n    public static void SelectionSort() {\n        for (int i = 0; i < N; i++) {  // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n            int mn = i;  // 가장 작은 값을 가리키는 인덱스\n            for (int j = i + 1; j < N; j++) {  // 현재 위치 이후의 요소들을 순회\n                if (arr[mn] > arr[j]) mn = j;  // 현재 최소값보다 작은 값을 찾으면 최소값 인덱스를 업데이트\n            }\n            if (i != mn) {  // 최소값 인덱스가 현재 위치와 다르면\n                int tmp = arr[i];  // 두 값을 교환\n                arr[i] = arr[mn];\n                arr[mn] = tmp;\n            }\n        }\n    }\n\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);  // 입력을 받기 위한 스캐너 객체 생성\n        N = sc.nextInt();  // 배열의 크기 입력\n        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();  // 배열의 요소 입력\n        SelectionSort();  // 선택 정렬 수행\n        for (int i = 0; i < N; i++) System.out.print(arr[i] + \" \");  // 정렬된 배열 출력\n        sc.close();  // 스캐너 닫기\n    }\n}",
        "위 예시처럼 주어진 코드를 예쁘게 정렬하고 핵심 내용에 대해 주석을 작성해 줘. 단, 주석은 설명하고자 하는 부분의 오른쪽에 위치해야 해. 코드와 주석이 아닌 부가적인 설명은 생략해 줘. ```로 감쌀 필요도 없어. "

    )

    private val cBlankPrompt = arrayListOf(
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\n\nll Query(ll n, ll l, ll r, ll st, ll ed) {\n    if (l > ed || r < st) // 탐색 범위를 벗어나는 경우\n        return 0;\n    if (l >= st && r <= ed) // 목표 범위에 속하는 경우\n        return SegTree[n];\n    ll mid = (l + r) / 2; // 중심값 선언\n    return Query(n * 2, l, mid, st, ed) + Query(n * 2 + 1, mid + 1, r, st, ed); // 왼쪽 구간과 오른쪽 구간을 탐색\n} \n\nll Update(ll n, ll l, ll r, ll pos, ll x) {\n    if (l > pos || r < pos) // 탐색범위를 벗어나면\n        return SegTree[n]; // 해당 세그먼트 트리의 값을 반환\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = x;\n        return SegTree[n]; // 새로 입력받은 값을 반환\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Update(n * 2, l, mid, pos, x) + Update(n * 2 + 1, mid + 1, r, pos, x); // 왼쪽 구간과 오른쪽 구간을 탐색\n    return SegTree[n];\n}\n\nll Init(ll n, ll l, ll r) {\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = arr[l]; // arr[l] 값이 상위노드로 넘어감\n        return SegTree[n];\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Init(n * 2, l, mid) + Init(n * 2 + 1, mid + 1, r); // 왼쪽 구간과 오른쪽 구간으로 분할\n    return SegTree[n];\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N >> M;\n    for (int i = 1; i <= N; i++)\n        cin >> arr[i]; // 원소 입력 받음\n\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\n    Init(1, 1, N); // 세그먼트 트리 초기화\n\n    for (int i = 0; i < M; i++) {\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x) // x가 1이면\n            cout << Query(1, 1, N, a, b) << \"\\n\"; // a번째부터 b번째까지의 합 출력\n        else // x가 0이면\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\n    }\n\n    return 0;\n}",
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\n\nll Query(ll n, ll l, ll r, ll st, ll ed) {\n    if (l > \$ed\$ || r < \$st\$) // 탐색 범위를 벗어나는 경우\n        return \$0\$;\n    if (l >= \$st\$ && r <= \$ed\$) // 목표 범위에 속하는 경우\n        return \$SegTree[n]\$;\n    ll mid = \$(l + r) / 2\$; // 중심값 선언\n    return Query(\$n * 2\$, \$l\$, \$mid\$, \$st\$, \$ed\$) + Query(\n * 2 + 1\$, \$mid + 1\$, \$r\$, \$st\$, \$ed\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n} \n\nll Update(ll n, ll l, ll r, ll pos, ll x) {\n    if (l > \$pos\$ || r < \$pos\$) // 탐색범위를 벗어나면\n        return \$SegTree[n]\$; // 해당 세그먼트 트리의 값을 반환\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$x\$;\n        return \$SegTree[n]\$; // 새로 입력받은 값을 반환\n    }\n    ll mid = \$(l + r) / 2\$; // 중심값 선언\n    SegTree[n] = Update(\$n * 2\$, \$l\$, \$mid\$, \$pos\$, \$x\$) + Update(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$pos\$, \$x\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n    return \$SegTree[n]\$;\n}\n\nll Init(ll n, ll l, ll r) {\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$arr[l]\$; // arr[l] 값이 상위노드로 넘어감\n        return \$SegTree[n]\$;\n    }\n    ll mid = \$(l + r) / 2\$; // 중심값 선언\n    SegTree[n] = Init(\$n * 2\$, \$l\$, \$mid\$) + Init(\$n * 2 + 1\$, \$mid + 1\$, \$r\$); // 왼쪽 구간과 오른쪽 구간으로 분할\n    return \$SegTree[n]\$;\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N >> M;\n    for (int i = 1; i <= N; i++)\n        cin >> arr[i]; // 원소 입력 받음\n\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\n    Init(1, 1, N); // 세그먼트 트리 초기화\n\n    for (int i = 0; i < M; i++) {\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x) // x가 1이면\n            cout << Query(1, 1, N, a, b) << \"\\n\"; // a번째부터 b번째까지의 합 출력\n        else // x가 0이면\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\n    }\n\n    return 0;\n}",
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N; // 배열의 크기를 나타내는 변수\nll arr[1005]; // 정렬할 배열\n\nvoid SelectionSort() { // 선택 정렬 함수\n    for (int i = 0; i < N; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n        ll mn = i; // 가장 작은 값을 가리키는 인덱스\n        // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\n        for (int j = i + 1; j < N; j++) {\n            if (arr[mn] > arr[j])\n                mn = j;\n        }\n        \n        if (i != mn) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\n            ll tmp = arr[i];\n            arr[i] = arr[mn];\n            arr[mn] = tmp;\n        }\n    }\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N; // 배열의 크기를 입력 받음\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\n        cin >> arr[i];\n\n    SelectionSort(); // 선택 정렬 수행\n\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\n        cout << arr[i] << \" \";\n\n    return 0;\n}",
        "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N; // 배열의 크기를 나타내는 변수\nll arr[1005]; // 정렬할 배열\n\nvoid SelectionSort() { // 선택 정렬 함수\n    for (int i = \$0\$; i < \$N\$; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n        ll mn = \$i\$; // 가장 작은 값을 가리키는 인덱스\n        // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\n        for (int j = \$i + 1\$; j < \$N\$; j++) {\n            if (arr[mn] > \$arr[j]\$)\n                mn = \$j\$;\n        }\n        \n        if (i != \$mn\$) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\n            ll tmp = \$arr[i]\$;\n            arr[i] = \$arr[mn]\$;\n            arr[mn] = \$tmp\$;\n        }\n    }\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N; // 배열의 크기를 입력 받음\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\n        cin >> arr[i];\n\n    SelectionSort(); // 선택 정렬 수행\n\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\n        cout << arr[i] << \" \";\n\n    return 0;\n}",
        "위 입력과 출력처럼 아래 코드를 파악하여 핵심 내용을 전부 \$로 감싸서 출력하라. 단, 다음의 규칙을 따른다. \n1. #include, #define 등 헤더 부분은 \$로 감싸지 않는다.\n2. 주석 파트는 무시한다. \n3. 'j < i' 처럼 '=', '<', '>', '<=', '>=', '==', '!=' 연산자가 등장하는 경우, 'j < \$i$'로 해당 연산자 뒤의 내용만 \$로 감싼다. \n4. 어떠한 변수를 처음 선언하는 경우, 변수의 이름은 \$로 감싸지 않는다. \n5. 'void bubble_sort(int list[], int n)'처럼 함수를 선언하는 줄의 경우, 소괄호 안의 변수를 \$로 감싸지 않는다.  해당 함수 선언의 중괄호 안의 내용만 판단한다. \n6. 'cin >> a >> b'나 'printf(\"%d\\n\", list[i])' 처럼 입력하거나 출력하는 부분은 \$로 감싸지 않는다. "
    )

    private val pythonBlankPrompt = arrayListOf(
        "import sys\nsys.setrecursionlimit(10**6)\n\ndef query(n, l, r, st, ed):\n    if l > ed or r < st: # 범위 밖인 경우 0 반환\n        return 0\n    if l >= st and r <= ed: # 범위 내에 완전히 속한 경우 현재 노드의 값 반환\n        return seg_tree[n]\n    mid = (l + r) // 2 # 중간 지점 계산\n    return query(n * 2, l, mid, st, ed) + query(n * 2 + 1, mid + 1, r, st, ed)\n\ndef update(n, l, r, pos, x):\n    if l > pos or r < pos: # 업데이트 위치가 범위 밖인 경우 현재 노드 값 반환\n        return seg_tree[n]\n    if l == r: # 리프 노드인 경우 값 업데이트\n        seg_tree[n] = x\n        return x\n    mid = (l + r) // 2 # 중간 지점 계산\n    seg_tree[n] = update(n * 2, l, mid, pos, x) + update(n * 2 + 1, mid + 1, r, pos, x)\n    return seg_tree[n]\n\ndef init(n, l, r):\n    if l == r: # 리프 노드인 경우 배열 값으로 초기화\n        seg_tree[n] = arr[l]\n        return arr[l]\n    mid = (l + r) // 2 # 중간 지점 계산\n    seg_tree[n] = init(n * 2, l, mid) + init(n * 2 + 1, mid + 1, r)\n    return seg_tree[n]\n\nN, M = map(int, input().split()) # 배열 크기 N과 쿼리 수 M 입력\narr = [0] + list(map(int, input().split())) # 배열 입력 (1-indexed)\nseg_tree = [0] * (4 * N) # 세그먼트 트리 초기화\ninit(1, 1, N) # 세그먼트 트리 생성\n\nfor _ in range(M):\n    x, a, b = map(int, input().split())\n    if x: # 쿼리 연산\n        print(query(1, 1, N, a, b))\n    else: # 업데이트 연산\n        update(1, 1, N, a, b)",
        "import sys\nsys.setrecursionlimit(10**6)\n\ndef query(n, l, r, st, ed):\n    if l > \$ed\$ or r < \$st\$: # 범위 밖인 경우 0 반환\n        return \$0\$\n    if l >= \$st\$ and r <= \$ed\$: # 범위 내에 완전히 속한 경우 현재 노드의 값 반환\n        return \$seg_tree[n]\$\n    mid = \$(l + r) // 2\$ # 중간 지점 계산\n    return query(\$n * 2\$, \$l\$, \$mid\$, \$st\$, \$ed\$) + query(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$st\$, \$ed\$)\n\ndef update(n, l, r, pos, x):\n    if l > \$pos\$ or r < \$pos\$: # 업데이트 위치가 범위 밖인 경우 현재 노드 값 반환\n        return \$seg_tree[n]\$\n    if l == \$r\$: # 리프 노드인 경우 값 업데이트\n        seg_tree[n] = \$x\$\n        return \$x\$\n    mid = $(l + r) // 2$ # 중간 지점 계산\n    seg_tree[n] = update(\$n * 2\$, \$l\$, \$mid\$, \$pos\$, \$x\$) + update(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$pos\$, \$x\$)\n    return \$seg_tree[n]\$\n\ndef init(n, l, r):\n    if l == \$r\$: # 리프 노드인 경우 배열 값으로 초기화\n        seg_tree[n] = \$arr[l]\$\n        return \$arr[l]\$\n    mid = \$(l + r) // 2\$ # 중간 지점 계산\n    seg_tree[n] = init(\$n * 2\$, \$l\$, \$mid\$) + init(\$n * 2 + 1\$, \$mid + 1\$, \$r\$)\n    return \$seg_tree[n]\$\n\nN, M = map(int, input().split()) # 배열 크기 N과 쿼리 수 M 입력\narr = [0] + list(map(int, input().split())) # 배열 입력 (1-indexed)\nseg_tree = [0] * (4 * N) # 세그먼트 트리 초기화\ninit(1, 1, N) # 세그먼트 트리 생성\n\nfor _ in range(M):\n    x, a, b = map(int, input().split())\n    if x: # 쿼리 연산\n        print(query(1, 1, N, a, b))\n    else: # 업데이트 연산\n        update(1, 1, N, a, b)",
        "def SelectionSort(arr):\n    for i in range(len(arr)): # 배열의 각 요소에 대해\n        mn = i # 현재 위치를 최소값 인덱스로 초기화\n        for j in range(i + 1, len(arr)): # 현재 위치 이후의 요소들을 순회\n            if arr[mn] > arr[j]: # 현재 최소값보다 작은 값을 찾으면\n                mn = j # 최소값 인덱스를 업데이트\n        if i != mn: # 최소값 인덱스가 현재 위치와 다르면\n            arr[i], arr[mn] = arr[mn], arr[i] # 두 값을 교환\n\nN = int(input()) # 배열 크기 N 입력\narr = list(map(int, input().split())) # 배열 요소 입력\nSelectionSort(arr) # 선택 정렬 함수 호출\nprint(*arr) # 정렬된 배열 출력",
        "def SelectionSort(arr):\n    for i in range(len(\$arr\$)): # 배열의 각 요소에 대해\n        mn = \$i\$ # 현재 위치를 최소값 인덱스로 초기화\n        for j in range(\$i + 1\$, len(\$arr\$)): # 현재 위치 이후의 요소들을 순회\n            if arr[mn] > \$arr[j]\$: # 현재 최소값보다 작은 값을 찾으면\n                mn = \$j\$ # 최소값 인덱스를 업데이트\n        if i != \$mn\$: # 최소값 인덱스가 현재 위치와 다르면\n            arr[i], arr[mn] = \$arr[mn]\$, \$arr[i]\$ # 두 값을 교환\n\nN = int(input()) # 배열 크기 N 입력\narr = list(map(int, input().split())) # 배열 요소 입력\nSelectionSort(arr) # 선택 정렬 함수 호출\nprint(*arr) # 정렬된 배열 출력",
        "위 입력과 출력처럼 아래 코드를 파악하여 핵심 내용을 전부 \$로 감싸서 출력하라. 단, 다음의 규칙을 따른다. \n1. import 등 헤더 부분은 \$로 감싸지 않는다.\n2. 주석 파트는 무시한다. \n3. 'j < i' 처럼 '=', '<', '>', '<=', '>=', '==', '!=' 연산자가 등장하는 경우, 'j < \$i\$'로 해당 연산자 뒤의 내용만 \$로 감싼다. \n4. 어떠한 변수를 처음 선언하는 경우, 변수의 이름은 \$로 감싸지 않는다. \n5. 'def bubble_sort(list, n)'의 'list'나 'n'처럼 함수를 선언하는 줄에 있는 소괄호 안의 변수는 \$로 감싸지 않는다.  해당 함수 선언의 중괄호 안의 내용만 판단한다. \n6. 'a = input()'이나 'print(*arr)' 처럼 입력하거나 출력하는 부분은 \$로 감싸지 않는다. "
    )

    private val javaBlankPrompt = arrayListOf(
        "import java.util.*;\n\npublic class SegmentTree {\n    static int N, M;\n    static int[] arr = new int[100005];\n    static ArrayList<Integer> SegTree = new ArrayList<>();\n\n    // 구간 합을 구하는 쿼리 함수\n    public static int Query(int n, int l, int r, int st, int ed) {\n        if (l > ed || r < st) return 0; // 구간이 범위를 벗어난 경우\n        if (l >= st && r <= ed) return SegTree.get(n); // 구간이 완전히 포함되는 경우\n        int mid = (l + r) / 2; // 중간 인덱스 계산\n        return Query(n * 2, l, mid, st, ed) + Query(n * 2 + 1, mid + 1, r, st, ed); // 좌우 자식을 재귀적으로 호출하여 합을 구함\n    }\n\n    // 세그먼트 트리 업데이트 함수\n    public static int Update(int n, int l, int r, int pos, int x) {\n        if (l > pos || r < pos) return SegTree.get(n); // 업데이트할 위치가 범위를 벗어난 경우\n        if (l == r) {\n            SegTree.set(n, x); // 값 업데이트 (리프 노드인 경우)\n            return SegTree.get(n);\n        }\n        int mid = (l + r) / 2; // 중간 인덱스 계산\n        SegTree.set(n, Update(n * 2, l, mid, pos, x) + Update(n * 2 + 1, mid + 1, r, pos, x)); // 좌우 자식을 재귀적으로 호출하여 값 업데이트\n        return SegTree.get(n);\n    }\n\n    // 세그먼트 트리 초기화 함수\n    public static int Init(int n, int l, int r) {\n        if (l == r) {\n            SegTree.set(n, arr[l]); // 입력 배열의 값으로 초기화 (리프 노드인 경우)\n            return SegTree.get(n);\n        }\n        int mid = (l + r) / 2; // 중간 인덱스 계산\n        SegTree.set(n, Init(n * 2, l, mid) + Init(n * 2 + 1, mid + 1, r)); // 좌우 자식을 재귀적으로 호출하여 초기화\n        return SegTree.get(n);\n    }\n\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        N = sc.nextInt();\n        M = sc.nextInt();\n        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();\n        for (int i = 0; i < 4 * N; i++) SegTree.add(0);\n        Init(1, 1, N);\n        for (int i = 0; i < M; i++) {\n            int x = sc.nextInt();\n            int a = sc.nextInt();\n            int b = sc.nextInt();\n            if (x != 0) System.out.println(Query(1, 1, N, a, b));\n            else Update(1, 1, N, a, b);\n        }\n        sc.close();\n    }\n}",
        "import java.util.*;\n\npublic class SegmentTree {\n\n    static int N, M;  // 배열의 크기 N과 쿼리의 수 M\n    static int[] arr = new int[100005];  // 입력 배열\n    static ArrayList<Integer> SegTree = new ArrayList<>();  // 세그먼트 트리 배열\n\n    public static int Query(int n, int l, int r, int st, int ed) { // 구간 합을 구하는 쿼리 함수\n        if (l > \$ed\$ || r < \$st\$) // 탐색 범위를 벗어나는 경우\n        return \$0\$;\n    if (l >= \$st\$ && r <= \$ed\$) // 목표 범위에 속하는 경우\n        return \$SegTree[n]\$;\n        int mid = (l + r) / 2;  // 중심값 선언\n    return Query(\$n * 2\$, \$l\$, \$mid\$, \$st\$, \$ed\$) + Query(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$st\$, \$ed\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n    }\n\n    public static int Update(int n, int l, int r, int pos, int x) { // 세그먼트 트리 업데이트 함수\n        if (l > \$pos\$ || r < \$pos\$) // 탐색범위를 벗어나면\n        return \$SegTree[n]\$; // 해당 세그먼트 트리의 값을 반환\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$x\$;\n        return \$SegTree[n]\$; // 새로 입력받은 값을 반환\n    }\n        int mid = \$(l + r) / 2\$; // 중심값 선언\n        SegTree.set(n, Update(\$n * 2\$, \$l\$, \$mid\$, \$pos\$, \$x\$) + Update(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$pos\$, \$x\$));  // 왼쪽 구간과 오른쪽 구간을 탐색\n        return \$SegTree.get(n)\$;\n    }\n\n    public static int Init(int n, int l, int r) { // 세그먼트 트리 초기화 함수\n        if (l == \$r\$) {  // 리프 노드인 경우\n            SegTree.set(n, \$arr[l]\$);  // 입력 배열의 값으로 초기화\n            return \$SegTree.get(n)\$;  \n        }\n        int mid = (l + r) / 2;  // 중간 인덱스 계산\n        SegTree.set(n, Init(\$n * 2\$, \$l\$, \$mid\$) + Init(\$n * 2 + 1\$, \$mid + 1\$, \$r\$));  // 좌우 자식을 재귀적으로 호출하여 초기화\n        return \$SegTree.get(n)\$;\n    }\n\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);  // 입력을 받기 위한 스캐너 객체 생성\n        N = sc.nextInt();  // 배열의 크기 입력\n        M = sc.nextInt();  // 쿼리의 수 입력\n        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();  // 배열의 요소 입력\n        for (int i = 0; i < 4 * N; i++) SegTree.add(0);  // 세그먼트 트리 크기가 4*N 인 벡터 생성\n        Init(1, 1, N);  // 세그먼트 트리 초기화\n        for (int i = 0; i < M; i++) {\n            int x = sc.nextInt();  // 쿼리 유형 입력 (0: 업데이트, 1: 구간 합)\n            int a = sc.nextInt();  // 첫 번째 인자 입력\n            int b = sc.nextInt();  // 두 번째 인자 입력\n            if (x != 0) System.out.println(Query(1, 1, N, a, b));  // 구간 합 쿼리 수행 후 출력\n            else Update(1, 1, N, a, b);  // 업데이트 쿼리 수행\n        }\n        sc.close();  // 스캐너 닫기\n    }\n}",
        "import java.util.*;\n\npublic class SelectionSort {\n    static int N;  // 배열의 크기\n    static int[] arr = new int[1005];  // 정렬할 배열\n\n    public static void SelectionSort() {\n        for (int i = 0; i < N; i++) {  // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n            int mn = i;  // 가장 작은 값을 가리키는 인덱스\n            for (int j = i + 1; j < N; j++) {  // 현재 위치 이후의 요소들을 순회\n                if (arr[mn] > arr[j]) mn = j;  // 현재 최소값보다 작은 값을 찾으면 최소값 인덱스를 업데이트\n            }\n            if (i != mn) {  // 최소값 인덱스가 현재 위치와 다르면\n                int tmp = arr[i];  // 두 값을 교환\n                arr[i] = arr[mn];\n                arr[mn] = tmp;\n            }\n        }\n    }\n\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);  // 입력을 받기 위한 스캐너 객체 생성\n        N = sc.nextInt();  // 배열의 크기 입력\n        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();  // 배열의 요소 입력\n        SelectionSort();  // 선택 정렬 수행\n        for (int i = 0; i < N; i++) System.out.print(arr[i] + \" \");  // 정렬된 배열 출력\n        sc.close();  // 스캐너 닫기\n    }\n}",
        "import java.util.*;\n\npublic class SelectionSort {\n\n    static int N;  // 배열의 크기\n    static int[] arr = new int[1005];  // 정렬할 배열\n\n    public static void SelectionSort() {\n        for (int i = 0; i < \$N\$; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n            int mn = i;  // 가장 작은 값을 가리키는 인덱스\n            for (int j = i + 1; j < \$N\$; j++) {  // 현재 위치 이후의 요소들을 순회\n                if (arr[\$mn\$] > arr[\$j\$])\n                    mn = j;  // 현재 최소값보다 작은 값을 찾으면 최소값 인덱스를 업데이트\n            }\n            if (i != \$mn\$) {  // 최소값 인덱스가 현재 위치와 다르면\n                int tmp = arr[\$i\$];  // 두 값을 교환\n                arr[\$i\$] = arr[\$mn\$];\n                arr[\$mn\$] = tmp;\n            }\n        }\n    }\n\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);  // 입력을 받기 위한 스캐너 객체 생성\n        N = sc.nextInt();  // 배열의 크기 입력\n        for (int i = 0; i < \$N\$; i++) arr[i] = sc.nextInt();  // 배열의 요소 입력\n        SelectionSort();  // 선택 정렬 수행\n        for (int i = 0; i < \$N\$; i++) System.out.print(arr[\$i\$] + \" \");  // 정렬된 배열 출력\n        sc.close();  // 스캐너 닫기\n    }\n}",
        "위 입력과 출력처럼 아래 코드를 파악하여 핵심 내용을 전부 \$로 감싸서 출력하라. 단, 다음의 규칙을 따른다. \n1. #include, #define 등 헤더 부분은 \$로 감싸지 않는다.\n2. 주석 파트는 무시한다. \n3. 'j < i' 처럼 '=', '<', '>', '<=', '>=', '==', '!=' 연산자가 등장하는 경우, 'j < \$i\$'로 해당 연산자 뒤의 내용만 \$로 감싼다. \n4. 'void bubble_sort(int list[], int n)'처럼 함수를 선언하는 경우는 \$로 감싸지 않는다.  해당 함수 선언의 중괄호 안의 내용만 판단한다. \n5. 'printf(\"%d\\n\", list[i])' 처럼 결과를 출력하는 부분은 \$로 감싸지 않는다. "
    )

    fun organizeAndAnnotate(code: String, language: Language): String {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")

        val annotatePrompt = when(language) {
            Language.C -> cAnnotatePrompt
            Language.PYTHON -> pythonAnnotatePrompt
            Language.JAVA -> javaAnnotatePrompt
        }

        val messageList = listOf(
            ChatMessageDto(
                role = "user",
                content = annotatePrompt[0]
            ),
            ChatMessageDto(
                role = "assistant",
                content = annotatePrompt[1]
            ),
            ChatMessageDto(
                role = "user",
                content = annotatePrompt[2]
            ),
            ChatMessageDto(
                role = "assistant",
                content = annotatePrompt[3]
            ),
            ChatMessageDto(
                role = "user",
                content = annotatePrompt[4]
            ),
            ChatMessageDto(
                role = "user",
                content = code
            )
        )

        val entity = HttpEntity(
            ChatRequestDto(
                model = "gpt-3.5-turbo",
                messages = messageList,
                temperature = 1.0,
                maxTokens = 2048
            ),
            headers
        )

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, ChatResponseDto::class.java)

        return response.body!!.choices[0].message.content
    }

    fun generateBlanks(code: String, language: Language): String {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")

        val blankPrompt = when(language) {
            Language.C -> cBlankPrompt
            Language.PYTHON -> pythonBlankPrompt
            Language.JAVA -> javaBlankPrompt
        }

        val messageList = listOf(
            ChatMessageDto(
                role = "user",
                content = blankPrompt[0]
            ),
            ChatMessageDto(
                role = "assistant",
                content = blankPrompt[1]
            ),
            ChatMessageDto(
                role = "user",
                content = blankPrompt[2]
            ),
            ChatMessageDto(
                role = "assistant",
                content = blankPrompt[3]
            ),
            ChatMessageDto(
                role = "user",
                content = blankPrompt[4]
            ),
            ChatMessageDto(
                role = "user",
                content = code
            )
        )

        val entity = HttpEntity(
            ChatRequestDto(
                model = "gpt-4",
                messages = messageList,
                temperature = 1.0,
                maxTokens = 2048
            ),
            headers
        )

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, ChatResponseDto::class.java)

        return response.body!!.choices[0].message.content
    }

}