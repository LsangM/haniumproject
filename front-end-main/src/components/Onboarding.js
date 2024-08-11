import React, { useState, useEffect, useRef } from "react";
import './styles/Onboarding.css';
import { useNavigate } from "react-router-dom";

const Onboarding = () => {

    const [imageIndex, setImageIndex] = useState(0);
    const [selected, setSelected] = useState(false);
    const [selectedTarget, setSelectedTarget] = useState(-1);
    const [workIndex, setWorkIndex] = useState(0);
    const page02Ref = useRef(null);
    const page03Ref = useRef(null);
    const page04Ref = useRef(null);
    const page05Ref = useRef(null);
    const navigate = useNavigate();

    const toLogIn = () => {
        navigate('/LogIn');
    };

    const toSignUp = () => {
        navigate('/SignUp');
    };

    // ----------------------------------Page 01---------------------------------------

    // 마우스 이벤트 핸들러를 등록합니다.
    useEffect(() => {
        const handleMouseMove = (e) => {
            // 마우스가 움직일 때마다 이미지를 변경합니다.
            const x = (e.clientX / window.innerWidth) * 10; // x 좌표
            const y = (e.clientY / window.innerHeight) * 10; // y 좌표
            const newIndex = Math.floor(Math.abs(x + y) % 6); // 이미지 개수에 따라 수정하세요
            setImageIndex(newIndex);
        };

        // 마우스 이벤트 리스너 등록
        window.addEventListener('mousemove', handleMouseMove);

        // 컴포넌트가 언마운트 될 때 이벤트 리스너를 제거합니다.
        return () => {
            window.removeEventListener('mousemove', handleMouseMove);
        };
    }, []);

    // IntersectionObserver
    useEffect(() => {
        // IntersectionObserver 초기화 및 설정
        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                // 요소가 화면에 나타났을 때의 처리
                if (entry.isIntersecting) {
                    // 처리 로직 추가
                    entry.target.style.opacity = 1;
                    entry.target.style.filter = 'blur(0px)';
                } else {
                    // 요소가 화면에서 사라졌을 때 로직 추가
                    // entry.target.style.opacity = 0;
                    // entry.target.style.filter = 'blur(30px)';
                }
            });
        }, {
            threshold: 0.5,
        });

        // 감시할 대상 요소를 지정
        if (page02Ref) {
            observer.observe(page02Ref.current);
        }
        if (page03Ref) {
            observer.observe(page03Ref.current);
        }
        if (page04Ref) {
            observer.observe(page04Ref.current);
        }
        if (page05Ref) {
            observer.observe(page05Ref.current);
        }

    }, []);

    // 이미지 경로를 배열에 저장합니다. 필요에 따라 이미지 경로를 수정하세요.
    const images = [
        '/img/hands/hands01.jpg',
        '/img/hands/hands02.jpg',
        '/img/hands/hands03.jpg',
        '/img/hands/hands04.jpg',
        '/img/hands/hands05.jpg',
        '/img/hands/hands06.jpg',
    ];

    // ----------------------------------Page 02---------------------------------------

    const selectables = [
        "세 가지 답변을 비교해서 원하는 답변을 선택할 수 있습니다.",
        "서로 다른 세 가지 답변 중 마음에 드는 것을 고를 수 있습니다.",
        "세 개의 답변을 만들어 보여드립니다. 고르기만 하세요.",
    ];

    const handleSelectableClick = (index) => {
        setSelected(true);
        setSelectedTarget(index);
    };

    // ----------------------------------Page 03---------------------------------------

    const cards = [
        {
            url: "/img/cards/card01.jpg",
            content: "❝\nAi는 마치 사람처럼 생각하고 배울 수 있는 똑똑한 프로그램을 말합니다. Ai는 궁금한 질문에 대답하고 사람들을 도울 수 있습니다.\n❞",
            id: 1,
        },
        {
            url: "/img/cards/card02.jpg",
            content: "❝\nAi는 많은 학습을 통해 그것을 분석하고 문제를 해결할 수 있는 기술입니다. 스마트폰의 음성 인공지능과 자율주행 자동차를 예로 들 수 있습니다.\n❞",
            id: 2,
        },
        {
            url: "/img/cards/card03.jpg",
            content: "❝\nAi는 기계가 인간과 유사한 학습, 추론 및 문제 해결 능력을 갖추도록 프로그래밍하거나 학습하는 기술 집합을 의미합니다.\n❞",
            id: 3,
        },
        {
            url: "/img/cards/card04.jpg",
            content: "❝\nAi는 기계학습 및 심층신경망 기술을 활용하여, 자동으로 지식을 추출하고 패턴을 학습하여 인간 수준 이상의 인식, 추론 및 결정을 수행하는 컴퓨터 시스템의 연구와 개발 분야입니다.\n❞",
            id: 4,
        },
    ];

    // ----------------------------------Page 04---------------------------------------

    const works = [
        {
            title: "☺ WORK 01",
            content: ["☺ Content 1-1", "☺ Content 1-2", "☺ Content 1-3"],
        },
        {
            title: "♔ WORK 02",
            content: ["♔ Content 2-1", "♔ Content 2-2", "♔ Content 2-3"],
        },
        {
            title: "✒ WORK 03",
            content: ["✒ Content 3-1", "✒ Content 3-2", "✒ Content 3-3"],
        },
    ];

    useEffect(() => {
        const intervalId = setInterval(() => {
          // 현재 인덱스가 2인 경우 0으로 다시 설정하고 아니면 1을 더합니다.
          setWorkIndex(prevIndex => (prevIndex === 2 ? 0 : prevIndex + 1));
        }, 2500);
    
        // 컴포넌트가 unmount 될 때 setInterval을 정리합니다.
        return () => {
          clearInterval(intervalId);
        };
    }, []); // 빈 배열을 전달하여 컴포넌트가 마운트될 때 한 번만 실행합니다.

    return (
        <div className="onboarding">
            <div className="header">
                <div className="button-wrapper">
                    <button className="logIn-button" onClick={toLogIn}>로그인</button>/
                    <button className="signUp-button" onClick={toSignUp}>회원가입</button>
                </div>
            </div>
            <div className="onboarding-content">
                <div className="page01">
                    <div className="left">
                        <span>{'{'} Ai {'}'} writing assistant<br /> Just for you</span>
                    </div>
                    <div className="center">
                        <img className="hands-img" alt="hands" src={images[imageIndex]} />
                    </div>
                    <div className="right">
                        <span>IN OTHER WORDS</span><br />
                        <span>인 아더 워즈</span>
                    </div>
                </div>
                <div className="page02" ref={page02Ref}>
                    <div className="page02-wrapper">
                        <div className="title">{'{'} 가능성 {'}'} 을 미리 확인하세요</div>
                        <div className="selectables-container">
                            {selectables.map((selectable, index) => (
                                <div className={`selectable ${selectedTarget !== index && selected ? 'not-selected' : ''}`} key={index} onClick={() => { handleSelectableClick(index) }}>
                                    <h4>{selectable}</h4>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
                <div ref={page03Ref} className="page03">
                    <div className="title">{'{'} 당신 {'}'} 만을 위한 글쓰기</div>
                    <div className="description">어떤 사람을 위한 어떤 글인지 알려주세요. 글이 달라집니다.</div>
                    <div className="card-container">
                        <div className="card">
                            <img src={cards[0].url} />
                            <div className="card-content"><div><p>{cards[0].content}</p></div></div>
                        </div>
                        <div className="card">
                            <img src={cards[1].url} />
                            <div className="card-content"><div><p>{cards[1].content}</p></div></div>
                        </div>
                    </div>
                    <div className="card-container">
                        <div className="card">
                            <img src={cards[2].url} />
                            <div className="card-content"><div><p>{cards[2].content}</p></div></div>
                        </div>
                        <div className="card">
                            <img src={cards[3].url} />
                            <div className="card-content"><div><p>{cards[3].content}</p></div></div>
                        </div>
                    </div>
                </div>
                <div className="page04" ref={page04Ref}>
                    <div className="title">당신만을 위한 {'{'} 작업공간 {'}'}</div>
                    <div className="description">작업물 관리는 걱정하지 마세요. 모두 저희가 관리하고 있어요.</div>
                    <div className="page04-container">
                        <div className="page-ex">
                            <div className="sidebar-ex">
                                {works.map((work, index) => (
                                    <div className={`work-ex ${index === workIndex ? 'selected' : ''}`}>
                                        <p>{work.title}</p>
                                    </div>
                                ))}
                            </div>
                            <div className="content-ex">
                                {works[workIndex].content.map((result, index) => (
                                    <div className="result-ex" key={`${workIndex}-${index}`} >
                                        <p style={{ animationDelay: `${index * 0.3}s` }}>{result}</p>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
                <div className="page05" ref={page05Ref}>
                    <div className="title">즉각적인 {'{'} 피드백 {'}'}</div>
                    <div className="description">전부 다시 쓸 필요 없어요. 바꾸고 싶은 부분은 말씀만 하세요.</div>
                    <div className="container">
                        <div className="paragraph01"><p>...</p></div>
                        <div className="paragraph02"><p>...</p></div>
                        <div className="paragraph03"><p>...</p></div>
                        <div className="paragraph04"><p>...</p></div>
                        <div className="paragraph05"><p>...</p></div>
                    </div>
                </div>
            </div>
            <div className="footer">IN OTHER WORDS</div>
        </div>
    );
};

export default Onboarding;