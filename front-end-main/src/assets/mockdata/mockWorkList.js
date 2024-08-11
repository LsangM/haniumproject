const mockWorkList = [
    {
        workId: 1,
        title: "작업 1",
        results: [
            {
                content: "Look again at that bright dot. It's right here, our home, ourselves. Everyone we love, know, heard of, and everything we hold dear exists or existed on that tiny speck. Our joy and sorrow, thousands of religions, ideologies, economic theories, hunters and gatherers, heroes and cowards, creators and destroyers of civilizations, kings and peasants, couples in love, mothers and fathers, hopeful children of the future, inventors and explorers, teachers of ethics and morals, corrupt politicians, 'superstars,' 'superhuman leaders,' saints and sinners, and all of human history's combined sum lived on this small celestial body, like dust motes floating in this sunlight.",
                original: "다시 이 빛나는 점을 보라. 그것은 바로 여기, 우리 집, 우리 자신인 것이다. 우리가 사랑하는 사람, 아는 사람, 소문으로 들었던 사람, 그 모든 것은 그 위에 있거나 또는 있었던 것이다. 우리의 기쁨과 슬픔, 숭상되는 수천의 종교, 이데올로기, 경제이론, 사냥꾼과 약탈자, 영웅과 겁쟁이, 문명의 창조자와 파괴자, 왕과 농민, 서로 사랑하는 남녀, 어머니와 아버지, 앞날이 촉망되는 아이들, 발명가와 개척자, 윤리 도덕의 교사들, 부패한 정치가들, '수퍼스타', '초인적 지도자', 성자와 죄인 등 인류의 역사에서 그 모든 것의 총합이 여기에, 이 햇빛 속에 떠도는 먼지와 같은 작은 천체에 살았던 것이다.",
            },
            {
                content: "The music that extraterrestrial life might convey is more likely to be polyphonic than a lonely flute's sound. We anticipate a symphony of harmonies and dissonances, a polyphonic nocturne in the interplay of cosmic music. If we were to listen to the fugue of galactic life composed of a billion stars, Earth's biologists would be left speechless by its splendor and grandeur.",
                original: "우주 생명이 들려줄 음악은 외로운 풀피릿 소리가 아니라 푸가일 가능성이 높다. 우리는 우주 음악에서 화음과 불협화음이 교차하는 다성부 대위법 야식의 둔주곡을 기대한다. 10억 개의 성부로 이루어진 은하 생명의 푸가를 듣는다면, 지구의 생물학자들은 그 화려함과 장엄함에 정신을 잃고 말 것이다.",
            },
            {
                content: "Biology and history share a common lesson for us, and that is by understanding others, we come to understand ourselves better. This is because it undoubtedly enables us to gain a deeper understanding of ourselves.",
                original: "생물학과 역사학이 우리에게 주는 교훈에는 공통점이 있다. 그것은 타자(남)를 이해함으로써 자신을 더 잘 이해하게 된다는 것이다. 왜냐하면 그것이 우리 자신을 더 잘 이해할 수 있게 해 줄 것임에 틀림없기 때문이다.",
            },
        ],
    },
    {
        workId: 2,
        title: "작업 2",
        results: [
            {
                content: "인공지능, 줄여서 AI, 는 컴퓨터 프로그램이 인간의 지능과 유사한 작업을 수행하도록 설계된 기술을 의미합니다. 이것은 컴퓨터가 데이터를 분석하고 판단하며, 문제를 해결하고, 학습하고 개선하는 데 사용됩니다. AI 시스템은 주로 기계 학습과 딥 러닝 같은 알고리즘을 사용하여 작동하며, 이러한 기술은 대용량 데이터를 처리하고 인간 수준의 성능을 달성하는 데 큰 역할을 합니다.",
                original: "인공지능이란 무엇인가?",
            },
            {
                content: "머신 러닝 (Machine Learning): 머신 러닝은 컴퓨터가 데이터에서 학습하고 패턴을 발견하여 예측하거나 결정을 내리는 데 사용되는 기술입니다. 이를 통해 컴퓨터는 경험을 통해 지능적인 결정을 내릴 수 있습니다.\n딥 러닝 (Deep Learning): 딥 러닝은 인공 신경망을 기반으로 한 머신 러닝의 한 분야로, 여러 층의 신경망을 사용하여 복잡한 작업을 수행하는 데 특히 효과적입니다. 이미지 인식, 음성 인식 및 자연어 처리에 많이 사용됩니다.\n자연어 처리 (NLP): 자연어 처리는 인간의 언어를 이해하고 생성하는 기술로, 기계 번역, 챗봇, 텍스트 분류 등에 적용됩니다.",
                original: "인공지능의 주요 개념은 무엇인가?",
            },
            {
                content: "작업 자동화: 인공지능은 반복적이고 지루한 작업을 자동화하여 인간의 생산성을 향상시킵니다. 이로써 인간은 더 창의적이고 전략적인 작업에 집중할 수 있게 됩니다.\n정확성과 효율성 향상: AI 시스템은 일관된 정확성으로 작업을 수행하며, 인간 에러를 최소화합니다. 이를 통해 비즈니스 프로세스의 효율성을 향상시키고 비용을 절감할 수 있습니다.\n데이터 분석 및 예측 능력: 인공지능은 대용량 데이터를 처리하고 패턴을 찾아 예측 모델을 개발하는 데 도움을 줍니다. 이는 비즈니스 의사 결정, 의료 진단, 금융 예측 등 다양한 분야에서 유용하게 활용됩니다.",
                original: "인공지능은 우리에게 무엇을 가져다줄 것인가?",
            },
        ],
    },
    {
        workId: 3,
        title: "작업 3",
        results: [
            {
                content: "Yesterday, my friend and I went to the store to buy some books. However, when we arrived at the store, it was closed early, so we couldn't buy the books. Instead, we went to the park to play frisbee, and I threw it really far, but my friend couldn't catch it, and it flew over the fence. We then had to climb the fence to get it back, but we weren't supposed to be there, so we were worried about getting caught by security. Luckily, we escaped without being seen by anyone, but we felt very guilty for our actions.",
                original: "Yesterday, me and my friend goes to store for buy some book. But, when we arrives at the store, they is closed early, so we can't to buy the book. Instead, we goes to the park for playing frisbee, and me throws it really far, but my friend can't catches it and it flies over the fence. We then have to climbs the fence for get it back, but we is not supposed to be there, so we worries about getting caught by security. Luckily, we escapes without seen by anyone, but we feels very guilty for our actions.",
            },
            {
                content: "Her new dog is very cute and friendly; it has brown fur, big blue eyes, and wags its tail all the time. She took it for a walk yesterday, but it didn't walk nicely on the leash; it pulled her all the way. She's very worried about its behavior and doesn't know what to do. Also, her neighbor, who has a cat, they don't get along well and often fight in the backyard.",
                original: "Her new dog is very cute and friendly, it has brown fur, big blue eyes, and wagging its tail all the time. She took it for a walk yesterday, but it didn't walked nicely on the leash, it pulls her all the way. She's very worried about its behavior and doesn't knows what to do. Also, her neighbor, who has a cat, they doesn't get along well and often fights in the backyard.",
            },
            {
                content: "I saw a movie last night with my friends, and it was so much fun. The actor in the movie was really good; he played the role very convincingly. But, the popcorn that we bought at the theater was too salty, and we couldn't eat it all. After the movie, we went to a cafe to drink coffee, and I ordered a caramel latte. When the waiter brought it to me, I noticed that they forgot to put whipped cream on top.",
                original: "I seen a movie last night with my friends, and it was so much fun. The actor in the movie was really good, he plays the role very convincing. But, the popcorn that we buys at the theater, it was too salty, and we couldn't eats it all. After the movie, we goes to a cafe for drink coffee, and I ordered a caramel latte. When the waiter brings it to me, I noticed that they forget to put whipped cream on top.",
            },
        ],
    },
]

export default mockWorkList;