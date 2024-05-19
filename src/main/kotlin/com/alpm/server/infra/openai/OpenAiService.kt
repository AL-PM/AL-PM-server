package com.alpm.server.infra.openai

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.alpm.server.infra.openai.dto.ChatMessageDto
import com.alpm.server.infra.openai.dto.ChatRequestDto
import com.alpm.server.infra.openai.dto.ChatResponseDto
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class OpenAiService (

    @Value("\${openai.token}")
    private val token: String,

    @Value("\${openai.url}")
    private val url: String,

    private val restTemplate: RestTemplate

) {

    private val client = OpenAI(token)

    fun organizeAndAnnotate(code: String): String {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")

        val messageList = listOf(
            ChatMessageDto(
                role = "user",
                content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\nll N, M;\\nll arr[100005];\\nvector<ll> SegTree;\\nll Query(ll n, ll l, ll r, ll st, ll ed){\\n    if (l>ed || r<st)\\n        return 0;\\n    if (l>=st && r<=ed)\\n        return SegTree[n];\\n    ll mid=(l+r)/2;\\n    return Query(n*2,l,mid,st,ed)+Query(n*2+1,mid+1,r,st,ed);\\n}\\nll Update(ll n, ll l, ll r, ll pos, ll x){\\n    if (l>pos || r<pos)\\n        return SegTree[n];\\n    if (l==r)\\n        return SegTree[n]=x;\\n    ll mid=(l+r)/2;\\n    return SegTree[n]=Update(n*2,l,mid,pos,x)+Update(n*2+1,mid+1,r,pos,x);\\n}\\nll Init(ll n, ll l, ll r){\\n    if (l==r)\\n        return SegTree[n]=arr[l];\\n    ll mid=(l+r)/2;\\n    return SegTree[n]=Init(n*2,l,mid)+Init(n*2+1,mid+1,r);\\n}\\nint main(){\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n    cin >> N >> M;\\n    for (int i=1;i<=N;i++)\\n        cin >> arr[i];\\n    SegTree.resize(4*N);\\n    Init(1,1,N);\\n    for (int i=0;i<M;i++){\\n        ll x, a, b;\\n        cin >> x >> a >> b;\\n        if (x)\\n            cout << Query(1,1,N,a,b) << \\\"\\\\n\\\";\\n        else\\n            Update(1,1,N,a,b);\\n    }\\n    return 0;\\n}"
            ),
            ChatMessageDto(
                role = "assistant",
                content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\n\\nll N, M;\\nll arr[100005];\\nvector<ll> SegTree;\\n\\nll Query(ll n, ll l, ll r, ll st, ll ed) {\\n    if (l > ed || r < st) // 탐색 범위를 벗어나는 경우\\n        return 0;\\n    if (l >= st && r <= ed) // 목표 범위에 속하는 경우\\n        return SegTree[n];\\n    ll mid = (l + r) / 2; // 중심값 선언\\n    return Query(n * 2, l, mid, st, ed) + Query(n * 2 + 1, mid + 1, r, st, ed); // 왼쪽 구간과 오른쪽 구간을 탐색\\n} \\n\\nll Update(ll n, ll l, ll r, ll pos, ll x) {\\n    if (l > pos || r < pos) // 탐색범위를 벗어나면\\n        return SegTree[n]; // 해당 세그먼트 트리의 값을 반환\\n    if (l == r) { // leaf node에 도달하면\\n        SegTree[n] = x;\\n        return SegTree[n]; // 새로 입력받은 값을 반환\\n    }\\n    ll mid = (l + r) / 2; // 중심값 선언\\n    SegTree[n] = Update(n * 2, l, mid, pos, x) + Update(n * 2 + 1, mid + 1, r, pos, x); // 왼쪽 구간과 오른쪽 구간을 탐색\\n    return SegTree[n];\\n}\\n\\nll Init(ll n, ll l, ll r) {\\n    if (l == r) { // leaf node에 도달하면\\n        SegTree[n] = arr[l]; // arr[l] 값이 상위노드로 넘어감\\n        return SegTree[n];\\n    }\\n    ll mid = (l + r) / 2; // 중심값 선언\\n    SegTree[n] = Init(n * 2, l, mid) + Init(n * 2 + 1, mid + 1, r); // 왼쪽 구간과 오른쪽 구간으로 분할\\n    return SegTree[n];\\n}\\n\\nint main() {\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n\\n    cin >> N >> M;\\n    for (int i = 1; i <= N; i++)\\n        cin >> arr[i]; // 원소 입력 받음\\n\\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\\n    Init(1, 1, N); // 세그먼트 트리 초기화\\n\\n    for (int i = 0; i < M; i++) {\\n        ll x, a, b;\\n        cin >> x >> a >> b;\\n        if (x) // x가 1이면\\n            cout << Query(1, 1, N, a, b) << \\\"\\\\n\\\"; // a번째부터 b번째까지의 합 출력\\n        else // x가 0이면\\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\\n    }\\n\\n    return 0;\\n}"
            ),
            ChatMessageDto(
                role = "user",
                content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\nll N;\\nll arr[1005];\\nvoid SelectionSort(){\\n    for (int i=0;i<N;i++){\\n        ll mn=i;\\n        for (int j=i+1;j<N;j++){\\n            if (arr[mn]>arr[j])\\n                mn=j;\\n        }\\n        if (i!=mn){\\n            ll tmp=arr[i];\\n            arr[i]=arr[mn];\\n            arr[mn]=tmp;\\n        }\\n    }\\n}\\nint main(){\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n    cin >> N;\\n    for (int i=0;i<N;i++)\\n        cin >> arr[i];\\n    SelectionSort();\\n    for (int i=0;i<N;i++)\\n        cout << arr[i] << \\\" \\\";\\n    return 0;\\n}"
            ),
            ChatMessageDto(
                role = "assistant",
                content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\n\\nll N; // 배열의 크기를 나타내는 변수\\nll arr[1005]; // 정렬할 배열\\n\\nvoid SelectionSort() { // 선택 정렬 함수\\n    for (int i = 0; i < N; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\\n        ll mn = i; // 가장 작은 값을 가리키는 인덱스\\n        \\n        for (int j = i + 1; j < N; j++) { // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\\n            if (arr[mn] > arr[j])\\n                mn = j;\\n        }\\n        \\n        if (i != mn) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\\n            ll tmp = arr[i];\\n            arr[i] = arr[mn];\\n            arr[mn] = tmp;\\n        }\\n    }\\n}\\n\\nint main() {\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n\\n    cin >> N; // 배열의 크기를 입력 받음\\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\\n        cin >> arr[i];\\n\\n    \\n    SelectionSort(); // 선택 정렬 수행\\n\\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\\n        cout << arr[i] << \\\" \\\";\\n\\n    return 0;\\n}"
            ),
            ChatMessageDto(
                role = "user",
                content = "위 예시처럼 주어진 코드를 예쁘게 정렬하고 핵심 내용에 대해 주석을 작성해 줘. 단, 주석은 설명하고자 하는 부분의 오른쪽에 위치해야 해. "
            ),
            ChatMessageDto(
                role = "user",
                content = code
            )
        )

        val entity = HttpEntity(ChatRequestDto(
            model = "gpt-3.5-turbo",
            messages = messageList,
            temperature = 1.0,
            maxTokens = 2048
        ), headers)

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, ChatResponseDto::class.java)

        println(response.body!!.choices[0].message.content)

        return response.body!!.choices[0].message.content
    }

    fun generateBlanks(code: String): String {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")

        val messageList = listOf(
            ChatMessageDto(
                role = "user",
                content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\n\nll Query(ll n, ll l, ll r, ll st, ll ed) {\n    if (l > ed || r < st) // 탐색 범위를 벗어나는 경우\n        return 0;\n    if (l >= st && r <= ed) // 목표 범위에 속하는 경우\n        return SegTree[n];\n    ll mid = (l + r) / 2; // 중심값 선언\n    return Query(n * 2, l, mid, st, ed) + Query(n * 2 + 1, mid + 1, r, st, ed); // 왼쪽 구간과 오른쪽 구간을 탐색\n} \n\nll Update(ll n, ll l, ll r, ll pos, ll x) {\n    if (l > pos || r < pos) // 탐색범위를 벗어나면\n        return SegTree[n]; // 해당 세그먼트 트리의 값을 반환\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = x;\n        return SegTree[n]; // 새로 입력받은 값을 반환\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Update(n * 2, l, mid, pos, x) + Update(n * 2 + 1, mid + 1, r, pos, x); // 왼쪽 구간과 오른쪽 구간을 탐색\n    return SegTree[n];\n}\n\nll Init(ll n, ll l, ll r) {\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = arr[l]; // arr[l] 값이 상위노드로 넘어감\n        return SegTree[n];\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Init(n * 2, l, mid) + Init(n * 2 + 1, mid + 1, r); // 왼쪽 구간과 오른쪽 구간으로 분할\n    return SegTree[n];\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N >> M;\n    for (int i = 1; i <= N; i++)\n        cin >> arr[i]; // 원소 입력 받음\n\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\n    Init(1, 1, N); // 세그먼트 트리 초기화\n\n    for (int i = 0; i < M; i++) {\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x) // x가 1이면\n            cout << Query(1, 1, N, a, b) << \"\\n\"; // a번째부터 b번째까지의 합 출력\n        else // x가 0이면\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\n    }\n\n    return 0;\n}"
            ),
            ChatMessageDto(
                role = "assistant",
                content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\n\nll Query(ll n, ll l, ll r, ll st, ll ed) {\n    if (l > \$ed\$ || r < \$st\$) // 탐색 범위를 벗어나는 경우\n        return $0$;\n    if (l >= \$st$ && r <= \$ed\$) // 목표 범위에 속하는 경우\n        return \$SegTree[n]\$;\n    ll mid = $(l + r) / 2$; // 중심값 선언\n    return Query(\$n * 2\$, \$l\$, \$mid\$, \$st\$, \$ed\$) + Query(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$st\$, \$ed\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n} \n\nll Update(ll n, ll l, ll r, ll pos, ll x) {\n    if (l > \$pos\$ || r < \$pos\$) // 탐색범위를 벗어나면\n        return \$SegTree[n]\$; // 해당 세그먼트 트리의 값을 반환\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$x\$;\n        return \$SegTree[n]\$; // 새로 입력받은 값을 반환\n    }\n    ll mid = $(l + r) / 2$; // 중심값 선언\n    SegTree[n] = Update(\$n * 2\$, \$l\$, \$mid\$, \$pos\$, \$x\$) + Update(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$pos\$, \$x\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n    return \$SegTree[n]\$;\n}\n\nll Init(ll n, ll l, ll r) {\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$arr[l]\$; // arr[l] 값이 상위노드로 넘어감\n        return \$SegTree[n]\$;\n    }\n    ll mid = $(l + r) / 2$; // 중심값 선언\n    SegTree[n] = Init(\$n * 2\$, \$l\$, \$mid\$) + Init(\$n * 2 + 1\$, \$mid + 1\$, \$r\$); // 왼쪽 구간과 오른쪽 구간으로 분할\n    return \$SegTree[n]\$;\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N >> M;\n    for (int i = 1; i <= N; i++)\n        cin >> arr[i]; // 원소 입력 받음\n\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\n    Init(1, 1, N); // 세그먼트 트리 초기화\n\n    for (int i = 0; i < M; i++) {\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x) // x가 1이면\n            cout << Query(1, 1, N, a, b) << \"\\n\"; // a번째부터 b번째까지의 합 출력\n        else // x가 0이면\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\n    }\n\n    return 0;\n}"
            ),
            ChatMessageDto(
                role = "user",
                content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N; // 배열의 크기를 나타내는 변수\nll arr[1005]; // 정렬할 배열\n\nvoid SelectionSort() { // 선택 정렬 함수\n    for (int i = 0; i < N; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n        ll mn = i; // 가장 작은 값을 가리키는 인덱스\n        // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\n        for (int j = i + 1; j < N; j++) {\n            if (arr[mn] > arr[j])\n                mn = j;\n        }\n        \n        if (i != mn) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\n            ll tmp = arr[i];\n            arr[i] = arr[mn];\n            arr[mn] = tmp;\n        }\n    }\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N; // 배열의 크기를 입력 받음\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\n        cin >> arr[i];\n\n    SelectionSort(); // 선택 정렬 수행\n\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\n        cout << arr[i] << \" \";\n\n    return 0;\n}"
            ),
            ChatMessageDto(
                role = "assistant",
                content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N; // 배열의 크기를 나타내는 변수\nll arr[1005]; // 정렬할 배열\n\nvoid SelectionSort() { // 선택 정렬 함수\n    for (int i = $0$; i < \$N\$; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n        ll mn = \$i\$; // 가장 작은 값을 가리키는 인덱스\n        // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\n        for (int j = \$i + 1\$; j < \$N\$; j++) {\n            if (arr[mn] > \$arr[j]\$)\n                mn = \$j\$;\n        }\n        \n        if (i != \$mn\$) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\n            ll tmp = \$arr[i]\$;\n            arr[i] = \$arr[mn]\$;\n            arr[mn] = \$tmp\$;\n        }\n    }\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N; // 배열의 크기를 입력 받음\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\n        cin >> arr[i];\n\n    SelectionSort(); // 선택 정렬 수행\n\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\n        cout << arr[i] << \" \";\n\n    return 0;\n}"
            ),
            ChatMessageDto(
                role = "user",
                content = "위 입력과 출력처럼 아래 코드를 파악하여 핵심 내용을 전부 \$로 감싸서 출력하라. 단, 다음의 규칙을 따른다. \n1. #include, #define 등 헤더 부분은 \$로 감싸지 않는다.\n2. 주석 파트는 무시한다. \n3. 'j < i' 처럼 '=', '<', '>', '<=', '>=', '==', '!=' 연산자가 등장하는 경우, 'j < \$i$'로 해당 연산자 뒤의 내용만 \$로 감싼다. \n4. 'void bubble_sort(int list[], int n)'처럼 함수를 선언하는 경우는 \$로 감싸지 않는다.  해당 함수 선언의 중괄호 안의 내용만 판단한다. \n5. 'printf(\"%d\\n\", list[i])' 처럼 결과를 출력하는 부분은 \$로 감싸지 않는다. "
            ),
            ChatMessageDto(
                role = "user",
                content = code
            )
        )

        val entity = HttpEntity(ChatRequestDto(
            model = "gpt-4",
            messages = messageList,
            temperature = 1.0,
            maxTokens = 2048
        ), headers)

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, ChatResponseDto::class.java)

        println(response.body!!.choices[0].message.content)

        return response.body!!.choices[0].message.content
    }

//    suspend fun organizeAndAnnotate(code: String): String {
//        val request = ChatCompletionRequest(
//            model = ModelId("gpt-3.5-turbo"),
//            messages = listOf(
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\nll N, M;\\nll arr[100005];\\nvector<ll> SegTree;\\nll Query(ll n, ll l, ll r, ll st, ll ed){\\n    if (l>ed || r<st)\\n        return 0;\\n    if (l>=st && r<=ed)\\n        return SegTree[n];\\n    ll mid=(l+r)/2;\\n    return Query(n*2,l,mid,st,ed)+Query(n*2+1,mid+1,r,st,ed);\\n}\\nll Update(ll n, ll l, ll r, ll pos, ll x){\\n    if (l>pos || r<pos)\\n        return SegTree[n];\\n    if (l==r)\\n        return SegTree[n]=x;\\n    ll mid=(l+r)/2;\\n    return SegTree[n]=Update(n*2,l,mid,pos,x)+Update(n*2+1,mid+1,r,pos,x);\\n}\\nll Init(ll n, ll l, ll r){\\n    if (l==r)\\n        return SegTree[n]=arr[l];\\n    ll mid=(l+r)/2;\\n    return SegTree[n]=Init(n*2,l,mid)+Init(n*2+1,mid+1,r);\\n}\\nint main(){\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n    cin >> N >> M;\\n    for (int i=1;i<=N;i++)\\n        cin >> arr[i];\\n    SegTree.resize(4*N);\\n    Init(1,1,N);\\n    for (int i=0;i<M;i++){\\n        ll x, a, b;\\n        cin >> x >> a >> b;\\n        if (x)\\n            cout << Query(1,1,N,a,b) << \\\"\\\\n\\\";\\n        else\\n            Update(1,1,N,a,b);\\n    }\\n    return 0;\\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.Assistant,
//                    content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\n\\nll N, M;\\nll arr[100005];\\nvector<ll> SegTree;\\n\\nll Query(ll n, ll l, ll r, ll st, ll ed) {\\n    if (l > ed || r < st) // 탐색 범위를 벗어나는 경우\\n        return 0;\\n    if (l >= st && r <= ed) // 목표 범위에 속하는 경우\\n        return SegTree[n];\\n    ll mid = (l + r) / 2; // 중심값 선언\\n    return Query(n * 2, l, mid, st, ed) + Query(n * 2 + 1, mid + 1, r, st, ed); // 왼쪽 구간과 오른쪽 구간을 탐색\\n} \\n\\nll Update(ll n, ll l, ll r, ll pos, ll x) {\\n    if (l > pos || r < pos) // 탐색범위를 벗어나면\\n        return SegTree[n]; // 해당 세그먼트 트리의 값을 반환\\n    if (l == r) { // leaf node에 도달하면\\n        SegTree[n] = x;\\n        return SegTree[n]; // 새로 입력받은 값을 반환\\n    }\\n    ll mid = (l + r) / 2; // 중심값 선언\\n    SegTree[n] = Update(n * 2, l, mid, pos, x) + Update(n * 2 + 1, mid + 1, r, pos, x); // 왼쪽 구간과 오른쪽 구간을 탐색\\n    return SegTree[n];\\n}\\n\\nll Init(ll n, ll l, ll r) {\\n    if (l == r) { // leaf node에 도달하면\\n        SegTree[n] = arr[l]; // arr[l] 값이 상위노드로 넘어감\\n        return SegTree[n];\\n    }\\n    ll mid = (l + r) / 2; // 중심값 선언\\n    SegTree[n] = Init(n * 2, l, mid) + Init(n * 2 + 1, mid + 1, r); // 왼쪽 구간과 오른쪽 구간으로 분할\\n    return SegTree[n];\\n}\\n\\nint main() {\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n\\n    cin >> N >> M;\\n    for (int i = 1; i <= N; i++)\\n        cin >> arr[i]; // 원소 입력 받음\\n\\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\\n    Init(1, 1, N); // 세그먼트 트리 초기화\\n\\n    for (int i = 0; i < M; i++) {\\n        ll x, a, b;\\n        cin >> x >> a >> b;\\n        if (x) // x가 1이면\\n            cout << Query(1, 1, N, a, b) << \\\"\\\\n\\\"; // a번째부터 b번째까지의 합 출력\\n        else // x가 0이면\\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\\n    }\\n\\n    return 0;\\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\nll N;\\nll arr[1005];\\nvoid SelectionSort(){\\n    for (int i=0;i<N;i++){\\n        ll mn=i;\\n        for (int j=i+1;j<N;j++){\\n            if (arr[mn]>arr[j])\\n                mn=j;\\n        }\\n        if (i!=mn){\\n            ll tmp=arr[i];\\n            arr[i]=arr[mn];\\n            arr[mn]=tmp;\\n        }\\n    }\\n}\\nint main(){\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n    cin >> N;\\n    for (int i=0;i<N;i++)\\n        cin >> arr[i];\\n    SelectionSort();\\n    for (int i=0;i<N;i++)\\n        cout << arr[i] << \\\" \\\";\\n    return 0;\\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.Assistant,
//                    content = "#include <bits/stdc++.h>\\n#define ll long long\\nusing namespace std;\\n\\nll N; // 배열의 크기를 나타내는 변수\\nll arr[1005]; // 정렬할 배열\\n\\nvoid SelectionSort() { // 선택 정렬 함수\\n    for (int i = 0; i < N; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\\n        ll mn = i; // 가장 작은 값을 가리키는 인덱스\\n        \\n        for (int j = i + 1; j < N; j++) { // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\\n            if (arr[mn] > arr[j])\\n                mn = j;\\n        }\\n        \\n        if (i != mn) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\\n            ll tmp = arr[i];\\n            arr[i] = arr[mn];\\n            arr[mn] = tmp;\\n        }\\n    }\\n}\\n\\nint main() {\\n    ios::sync_with_stdio(0);\\n    cin.tie(0);\\n\\n    cin >> N; // 배열의 크기를 입력 받음\\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\\n        cin >> arr[i];\\n\\n    \\n    SelectionSort(); // 선택 정렬 수행\\n\\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\\n        cout << arr[i] << \\\" \\\";\\n\\n    return 0;\\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = "위 예시처럼 주어진 코드를 예쁘게 정렬하고 핵심 내용에 대해 주석을 작성해 줘. 단, 주석은 설명하고자 하는 부분의 오른쪽에 위치해야 해. "
//                ),
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = code
//                )
//            ),
//            temperature = 1.0,
//            maxTokens = 2048,
//            topP = 1.0,
//            frequencyPenalty = 0.0,
//            presencePenalty = 0.0
//        )
//
//        val response = client.chatCompletion(request)
//
//        println(response.choices[0].message.content!!)
//
//        return response.choices[0].message.content!!
//    }

//    suspend fun generateBlanks(code: String): String {
//        val request = ChatCompletionRequest(
//            model = ModelId("gpt-4"),
//            messages = listOf(
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\n\nll Query(ll n, ll l, ll r, ll st, ll ed) {\n    if (l > ed || r < st) // 탐색 범위를 벗어나는 경우\n        return 0;\n    if (l >= st && r <= ed) // 목표 범위에 속하는 경우\n        return SegTree[n];\n    ll mid = (l + r) / 2; // 중심값 선언\n    return Query(n * 2, l, mid, st, ed) + Query(n * 2 + 1, mid + 1, r, st, ed); // 왼쪽 구간과 오른쪽 구간을 탐색\n} \n\nll Update(ll n, ll l, ll r, ll pos, ll x) {\n    if (l > pos || r < pos) // 탐색범위를 벗어나면\n        return SegTree[n]; // 해당 세그먼트 트리의 값을 반환\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = x;\n        return SegTree[n]; // 새로 입력받은 값을 반환\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Update(n * 2, l, mid, pos, x) + Update(n * 2 + 1, mid + 1, r, pos, x); // 왼쪽 구간과 오른쪽 구간을 탐색\n    return SegTree[n];\n}\n\nll Init(ll n, ll l, ll r) {\n    if (l == r) { // leaf node에 도달하면\n        SegTree[n] = arr[l]; // arr[l] 값이 상위노드로 넘어감\n        return SegTree[n];\n    }\n    ll mid = (l + r) / 2; // 중심값 선언\n    SegTree[n] = Init(n * 2, l, mid) + Init(n * 2 + 1, mid + 1, r); // 왼쪽 구간과 오른쪽 구간으로 분할\n    return SegTree[n];\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N >> M;\n    for (int i = 1; i <= N; i++)\n        cin >> arr[i]; // 원소 입력 받음\n\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\n    Init(1, 1, N); // 세그먼트 트리 초기화\n\n    for (int i = 0; i < M; i++) {\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x) // x가 1이면\n            cout << Query(1, 1, N, a, b) << \"\\n\"; // a번째부터 b번째까지의 합 출력\n        else // x가 0이면\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\n    }\n\n    return 0;\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.Assistant,
//                    content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N, M;\nll arr[100005];\nvector<ll> SegTree;\n\nll Query(ll n, ll l, ll r, ll st, ll ed) {\n    if (l > \$ed\$ || r < \$st\$) // 탐색 범위를 벗어나는 경우\n        return $0$;\n    if (l >= \$st$ && r <= \$ed\$) // 목표 범위에 속하는 경우\n        return \$SegTree[n]\$;\n    ll mid = $(l + r) / 2$; // 중심값 선언\n    return Query(\$n * 2\$, \$l\$, \$mid\$, \$st\$, \$ed\$) + Query(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$st\$, \$ed\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n} \n\nll Update(ll n, ll l, ll r, ll pos, ll x) {\n    if (l > \$pos\$ || r < \$pos\$) // 탐색범위를 벗어나면\n        return \$SegTree[n]\$; // 해당 세그먼트 트리의 값을 반환\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$x\$;\n        return \$SegTree[n]\$; // 새로 입력받은 값을 반환\n    }\n    ll mid = $(l + r) / 2$; // 중심값 선언\n    SegTree[n] = Update(\$n * 2\$, \$l\$, \$mid\$, \$pos\$, \$x\$) + Update(\$n * 2 + 1\$, \$mid + 1\$, \$r\$, \$pos\$, \$x\$); // 왼쪽 구간과 오른쪽 구간을 탐색\n    return \$SegTree[n]\$;\n}\n\nll Init(ll n, ll l, ll r) {\n    if (l == \$r\$) { // leaf node에 도달하면\n        SegTree[n] = \$arr[l]\$; // arr[l] 값이 상위노드로 넘어감\n        return \$SegTree[n]\$;\n    }\n    ll mid = $(l + r) / 2$; // 중심값 선언\n    SegTree[n] = Init(\$n * 2\$, \$l\$, \$mid\$) + Init(\$n * 2 + 1\$, \$mid + 1\$, \$r\$); // 왼쪽 구간과 오른쪽 구간으로 분할\n    return \$SegTree[n]\$;\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N >> M;\n    for (int i = 1; i <= N; i++)\n        cin >> arr[i]; // 원소 입력 받음\n\n    SegTree.resize(4 * N); // 세그먼트 트리 크기가 4*N 인 벡터 생성\n    Init(1, 1, N); // 세그먼트 트리 초기화\n\n    for (int i = 0; i < M; i++) {\n        ll x, a, b;\n        cin >> x >> a >> b;\n        if (x) // x가 1이면\n            cout << Query(1, 1, N, a, b) << \"\\n\"; // a번째부터 b번째까지의 합 출력\n        else // x가 0이면\n            Update(1, 1, N, a, b); // a번째 원소를 b로 변경\n    }\n\n    return 0;\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N; // 배열의 크기를 나타내는 변수\nll arr[1005]; // 정렬할 배열\n\nvoid SelectionSort() { // 선택 정렬 함수\n    for (int i = 0; i < N; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n        ll mn = i; // 가장 작은 값을 가리키는 인덱스\n        // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\n        for (int j = i + 1; j < N; j++) {\n            if (arr[mn] > arr[j])\n                mn = j;\n        }\n        \n        if (i != mn) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\n            ll tmp = arr[i];\n            arr[i] = arr[mn];\n            arr[mn] = tmp;\n        }\n    }\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N; // 배열의 크기를 입력 받음\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\n        cin >> arr[i];\n\n    SelectionSort(); // 선택 정렬 수행\n\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\n        cout << arr[i] << \" \";\n\n    return 0;\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.Assistant,
//                    content = "#include <bits/stdc++.h>\n#define ll long long\nusing namespace std;\n\nll N; // 배열의 크기를 나타내는 변수\nll arr[1005]; // 정렬할 배열\n\nvoid SelectionSort() { // 선택 정렬 함수\n    for (int i = $0$; i < \$N\$; i++) { // 배열의 모든 요소를 확인하며 가장 작은 값을 찾아서 앞으로 이동시킴\n        ll mn = \$i\$; // 가장 작은 값을 가리키는 인덱스\n        // 현재 인덱스 이후의 요소 중에서 가장 작은 값을 찾음\n        for (int j = \$i + 1\$; j < \$N\$; j++) {\n            if (arr[mn] > \$arr[j]\$)\n                mn = \$j\$;\n        }\n        \n        if (i != \$mn\$) { // 현재 인덱스와 가장 작은 값의 인덱스가 다를 경우 위치를 변경함\n            ll tmp = \$arr[i]\$;\n            arr[i] = \$arr[mn]\$;\n            arr[mn] = \$tmp\$;\n        }\n    }\n}\n\nint main() {\n    ios::sync_with_stdio(0);\n    cin.tie(0);\n\n    cin >> N; // 배열의 크기를 입력 받음\n    for (int i = 0; i < N; i++) // 배열의 요소를 입력 받음\n        cin >> arr[i];\n\n    SelectionSort(); // 선택 정렬 수행\n\n    for (int i = 0; i < N; i++) // 정렬된 배열을 출력\n        cout << arr[i] << \" \";\n\n    return 0;\n}"
//                ),
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = "위 입력과 출력처럼 아래 코드를 파악하여 핵심 내용을 전부 \$로 감싸서 출력하라. 단, 다음의 규칙을 따른다. \n1. #include, #define 등 헤더 부분은 \$로 감싸지 않는다.\n2. 주석 파트는 무시한다. \n3. 'j < i' 처럼 '=', '<', '>', '<=', '>=', '==', '!=' 연산자가 등장하는 경우, 'j < \$i$'로 해당 연산자 뒤의 내용만 \$로 감싼다. \n4. 'void bubble_sort(int list[], int n)'처럼 함수를 선언하는 경우는 \$로 감싸지 않는다.  해당 함수 선언의 중괄호 안의 내용만 판단한다. \n5. 'printf(\"%d\\n\", list[i])' 처럼 결과를 출력하는 부분은 \$로 감싸지 않는다. "
//                ),
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = code
//                )
//            ),
//            temperature = 1.0,
//            maxTokens = 2048,
//            topP = 1.0,
//            frequencyPenalty = 0.0,
//            presencePenalty = 0.0
//        )
//
//        val response = client.chatCompletion(request)
//
//        println(response.choices[0].message.content!!)
//
//        return response.choices[0].message.content!!
//    }

}