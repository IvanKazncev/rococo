create extension if not exists "pgcrypto";

create table if not exists "artists"
(
    id        varchar(255)  UNIQUE NOT NULL DEFAULT (gen_random_uuid()),
    name      varchar(255)  unique not null,
    biography varchar(2000) not null,
    photo     TEXT,
    primary key (id)
);

insert into artists (
    id,
    name,
    biography,
    photo
) values
(
    '104f76ce-0508-49d4-8967-fdf1ebb8cf45',
    'Иван Шишкин',
    'Один из крупнейших русских пейзажистов, известный своими картинами природы.',
    'https://example.com/images/shishkin.jpg'
),
(
    '343f5bdc-b6ed-42ef-a1fa-c3e5b9e734bd',
    'Илья Репин',
    'Известный русский художник-реалист, автор картин ''Бурлаки на Волге'' и ''Иван Грозный и сын его Иван''.',
    'https://example.com/images/repin.jpg'
),
(
    '4a7461cd-d647-4671-9e35-e08655583883',
    'Василий Суриков',
    'Великий исторический живописец, создавший картины ''Утро стрелецкой казни'', ''Меншиков в Березове'' и др.',
    'https://example.com/images/surikov.jpg'
),
(
    '5c9722eb-f087-4555-8a69-f5135867321a',
    'Виктор Васнецов',
    'Художник, прославившийся работами на темы русской истории и мифологии, известен картиной ''Богатыри''.',
    'https://example.com/images/vasnetsov.jpg'
),
(
    '8224b248-7246-4659-8e74-ab11942a746f',
    'Михаил Врубель',
    'Творчество художника характеризуется оригинальностью стиля и богатством символики, знаменит картиной ''Демон сидящий''.',
    'https://example.com/images/vrubel.jpg'
),
(
    'c34287ff-148a-4215-b1a6-da6064342804',
    'Валентин Серов',
    'Мастер портрета и жанровых сцен, создал известные произведения ''Девочка с персиками'' и ''Петрушка''.',
    'https://example.com/images/serov.jpg'
),
(
    'f47a115a-6573-4575-8e26-1152a573637b',
    'Арсений Иванов',
    'Современный российский художник, работавший в жанре реализма и сюрреализма.',
    'https://example.com/images/arson.jpg'
);