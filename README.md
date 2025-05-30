# گزارش آزمایش هفتم 

## بخش اول: بازآرایی پروژه‌ی MiniJava

### مورد اول: Facade

از آن‌جایی که CodeGenerator تعداد زیادی تابع public و interfaceای پیچیده داشت اما تنها از برخی توابع آن توسط Parser استفاده می‌شد، با افزودن CodeGeneratorFacade سعی کردیم interface بیرونی آن را ساده‌تر کنیم.
بنابراین کلاس facade تنها دو تابع دارد که هر دو توسط parser استفاده می‌شوند و پیچیدگی‌های پیاده‌سازی نیز در آن نیامده است.

### مورد دوم: Facade

کلاس lexicalAnalyzer از دید parser کارکرد بسیار ساده‌ای دارد و تنها باید token بعدی را به او بدهد. اما جزئیات پیاده‌سازی ظاهر این کلاس را پیچیده کرده است بنابراین با افزودن LexicalAnalyzerFacade این جزئیات را از دید parser پنهان کردیم.
در این کلاس تنها تابع getNextToken قرار دارد و پیاده‌سازی آن بسیار ساده است.

### مورد سوم: State

در کلاس CodeGenerator تعداد زیادی switch روی SymbolTypeهای Symbolها قرار داشت که در صورت bool یا int بودن varType متناسب با آن را مشخص می‌کرد. ما سعی کردمی با استفاده از state pattern این switchها را به یک method call تبدیل کنیم. برای این کار  SymbolType را از enum به abstract class تغییر دادیم و به ازای هر نوع آن یک کلاس فرزند برای آن ساختیم و تعیین varType متناسب را به کلاس‌های فرزند سپردیم. با این کار تعداد زیادی از switchهای CodeGenerator از بین رفتند.

### مورد چهارم: Separate Query from Modifier

در کلاس Memory تابع saveMemory عملا دو عملیات متفاوت نگه داشتن یک واحد حافظه برای یک بلوک کد و همچنین برگرداندن آدرس آن بلوک از حافظه را بر عهده دارد. با جدا کردن query از این تابع و افزودن تابع getMemorySize به کلاس Memory، اصل single responsibility را رعایت کردیم.
بنابراین تابع اولیه تنها عمل modify و تابع جدید تنها عمل query را انجام می‌دهد.
این تابع تنها در CodeGenerator استفاده شده بود که تغییرات لازم مربوط به آن را نیز اعمال کردیم.

### مورد پنجم : self encapsulate field
این پترن بیان میکند که برای دسترسی به فیلدهای خصوصی یک کلاس از درون همان کلاس، باید متدهای دسترسی getter و setter تعریف کنیم و مستقیم به آنها دسترسی نداشته باشیم.
بنابراین در کلاس Memory که فیلد های خصوصی زیادی داریم که از درون همین کلاس نیز مستقیم دسترسی گرفته اند این پترن را اعمال کردیم: به این صورت که برای تمامی این فیلد ها getter و setter تعریف کردیم و دسترسی فقط از طریق این متدها به فیلدها صورت میگیرد.

### مورد ششم (پترن دلخواه): encapsulate field
این پترن بیان میکند که فیلد های یک شی نباید به صورت عمومی در معرض دید قرار بگیرد، و در عوض تمام دسترسی ها از خارج از شی باید از طریق مندهای دسترسی getter و setter صورت بگیرد.
درنتیجه فیلد های کلاس Action را private کرده و برای آنها getter و setter تعریف کردیم.

### مورد هفتم (پترن دلخواه): extract method 
این پترن بیان میکند که تکه ای از کد را که قابلیت جداسازی دارد را جدا کرده و به یک متد مجزا تبدیل کنیم و در متد قدیمی این متد را فراخوانی کنیم.
در کلاس ParseTable میبینیم که متد ParseTable طولانی است و وضایف متعددی از قببل پاک سازی براکت ها، جداسازی سطرها، پردازش هدر برای ترمینال ها و غیرترمینال ها و پرکردن جداول goto و action. بنابراین هر کدام از این بخش ها را به متدی خصوصی تبدیل میکنیم.

### مورد هشتم (پترن دلخواه): parameterize method
در این پترن، متد هایی که عملیات یکسانی انجام میدهند و فقط در مثادیر درونی متفاوت هستند را یکی میکند و مقادیر خاص هرکدام را به عنوان پارامتر به این متد جدید پاس میدهد.
در کلاس CodeGenerator سه تابع add, sub, mult داریم که منطق مشابهی دارند. بنابراین متد arithmeticOperation را جایگزین این سه متد کردیم.


## بخش دوم: سوالات

### تعریف کد تمیز
کد تمیز (Clean Code) به کدی اطلاق می‌شود که:

- خوانا و قابل فهم باشد.
- بدون نیاز به توضیحات زیاد، منطق آن واضح باشد.
- قابلیت نگهداری و توسعه را داشته باشد.
- تست‌پذیر، قابل گسترش و خالی از خطاهای منطقی باشد.

### تعریف بدهی فنی
بدهی فنی (Technical Debt) به معنای صرف نظر کردن از کیفیت بلندمدت کد برای دستیابی به سرعت در توسعه کوتاه‌مدت است. این مفهوم شبیه به بدهی مالی است: هر تصمیم پایین‌کیفیت در کد، در آینده باعث افزایش هزینه نگهداری و توسعه می‌شود.

### تعریف بوی بد (Bad Smell) 
بوی بد (Code Smell) به الگوهایی در کد اشاره دارد که نشانه‌ای از طراحی ضعیف، عدم رعایت اصول طراحی، یا مشکلات بالقوه در آینده هستند. بوی بد لزوماً باعث خرابی برنامه نمی‌شود، اما نشانه‌ای از نیاز به بازآرایی است.

### انواع بوی بد و توضیحی من باب آن‌ها 
نوع ۱: Bloaters
این دسته از بوها مربوط به قسمت‌هایی از کد، متدها و کلاس‌هایی است که به حدی زیاد شده‌اند که با آن‌ها کار کردن دشوار می‌شود. این بوها معمولاً یکدفعه ظاهر نمی‌شوند، بلکه در طول زمان و با تکامل برنامه (و به خصوص وقتی کسی سعی در رفع آن‌ها نکند) به وجود می‌آیند.

نوع ۲: Object-Orientation Abusers
این بوها نشان می‌دهند که اصول برنامه‌نویسی شیءگرا به خوبی پیاده‌سازی نشده یا به درستی درک نشده‌اند.

نوع ۳: Change Preventers
این بوها نشان می‌دهند که اگر بخواهید یک تغییر کوچک در کد اعمال کنید، مجبورید تغییرات زیادی را در نقاط مختلف برنامه انجام دهید. این موضوع باعث پیچیده و گران شدن توسعه برنامه می‌شود.

نوع ۴: Dispensables
این بوها به عناصری اشاره دارند که بدون هدف هستند و حذف آن‌ها باعث تمیزتر، کارآمدتر و قابل فهم‌تر شدن کد می‌شود.

نوع ۵: Couplers
این گروه از بوها به معنای وجود وابستگی‌های بیش از حد بین کلاس‌ها یا جایگزینی وابستگی با فراخوانی‌های زیاد غیرضروری است. وقتی گروهی از داده‌ها (مثلاً چند پارامتر خاص) همیشه با هم ظاهر می‌شوند و احتمالاً باید در یک کلاس یا شی قرار گیرند.


### بررسی عمیق Feature Envy (دسته‌بندی، راه‌حل و نادیده گرفتن)

بوی بد Feature Envy در دسته‌بندی Couplers جای می‌گیرد. برای حل این مشکل، بسته به شرایط، از یکی از سه حالت زیر بهره مي‌بریم:

- اگر مشخص باشد که یک متد باید به جای دیگری منتقل شود، از الگوی Move Method استفاده کنید.
- اگر تنها بخشی از یک متد به داده‌های یک شیء دیگر دسترسی داشته باشد، از Extract Method استفاده کنید و آن بخش را به یک متد جداگانه منتقل کنید.
- اگر یک متد از چندین کلاس دیگر استفاده می‌کند، ابتدا تعیین کنید که کدام کلاس بیشترین داده‌های مورد استفاده را دارد. سپس متد را در کنار داده‌های دیگر در همان کلاس قرار دهید. راه دیگر، استفاده از Extract Method برای تقسیم متد به چندین بخش است که هر کدام می‌توانند در کلاس‌های مختلف قرار بگیرند. 

این مشکلی گاهی اوقات نادیده نیز گرفته می‌شود. فرضا گاهی اوقات behavior به صورت عمدی از کلاسی که داده‌ها را در خود دارد، جدا نگه داشته می‌شود. مزیت اصلی این کار، امکان تغییر پویای رفتار است.


### تفاوت‌های بین "Code smell" و "Bug"

 بوی بد نشانه‌ای از ضعف در طراحی یا نوشتار کد است — مثل متدهای خیلی طولانی، کد تکراری یا طراحی ضعیف کلاس‌ها. این موارد معمولاً باعث خرابی برنامه نمی‌شوند، اما درک و نگهداری کد را سخت می‌کنند و در بلندمدت ممکن است منجر به بروز باگ شوند.
در مقابل، یک باگ  یک خطای واقعی در منطق برنامه است که باعث می‌شود خروجی اشتباه تولید شود، برنامه کرش کند یا رفتار غیرمنتظره‌ای نشان دهد. باگ باید فوراً برطرف شود تا عملکرد نرم‌افزار درست شود.

پس می‌توان گفت
بوی بد کد = نشانه‌ای از کیفیت پایین کد
باگ = خطای واقعی در رفتار یا منطق برنامه


### اشاره به ده مدل بوی بد در پروژه تبدیل مدل به سی

مشکل ۱: Long Method
جایگاه : Parser.java – متد semanticFunction(int func, Token next)
توضیحات : این متد بیش از 100 خط کد دارد و تمام عملیات‌های سемانتیکی (مانند جمع، ضرب، مقایسه و غیره) را با استفاده از یک switch-case بزرگ پوشش می‌دهد.


مشکل ۲: Large Class
جایگاه : SymbolTable.java
توضیحات : کلاس SymbolTable شامل متدهای زیادی برای مدیریت نمادها، متدها، فیلدها، متغیرهای محلی و غیره است. این کلاس بیش از 500 خط کد دارد.

مشکل ۳: Primitive Obsession
جایگاه : Address.java – استفاده مستقیم از int برای num و varType برای تعیین نوع آدرس
توضیحات : به جای استفاده از انواع داده‌ای غنی‌تر، از متغیرهای اولیه (int, String) برای نمایش وضعیت‌های پیچیده استفاده شده است.

مشکل ۴: Long Parameter List
جایگاه : Memory.add3AddressCode(Operation op, Address opr1, Address opr2, Address opr3)
توضیحات : این متد چهار پارامتر دریافت می‌کند که اغلب در تمام کلاس‌ها استفاده می‌شوند.

مشکل ۵: Switch Statements
جایگاه : CodeGenerator.java – متد semanticFunction(int func, Token next)
توضیحات : این متد دارای یک دستور switch است که بر اساس func عملیات‌های مختلفی مانند add, assign, print و غیره را انجام می‌دهد.

مشکل ۶: Refused Bequest
جایگاه : BooleanSymbolType و IntegerSymbolType از SymbolType ارث می‌برند ولی فقط یک متد را override می‌کنند.
توضیحات : تمامی کلاس‌های فرزند تنها یک متد getVarType() را override می‌کنند و دیگر ویژگی‌ها/متدهای والد را استفاده نمی‌کنند.

مشکل ۷: Divergent Change
جایگاه : CodeGeneratorFacade و CodeGenerator
توضیحات : هر تغییر در نحوه کار ساختن کد سه آدرسی (3AddressCode) یا کارکرد semanticFunction ممکن است نیازمند تغییر همزمان در هر دو کلاس باشد.

مشکل ۸: Shotgun Surgery
جایگاه : SymbolTable.java و Method در داخل آن
توضیحات : تغییر در نحوه مدیریت متغیرهای محلی (localVariable) یا پارامترها (parameters) ممکن است نیازمند تغییرات متعدد در متدهای getVariable, addParameter, addMethodLocalVariable باشد.


مشکل ۹: Feature Envy
جایگاه : Parser.java – متد startParse(java.util.Scanner sc) زیادی از symbolStack و ss (stacks) استفاده می‌کند.
توضیحات : این متد بیشتر روی داده‌های symbolStack و ss عمل می‌کند و این داده‌ها در واقع توسط CodeGenerator مدیریت می‌شوند.

مشکل ۱۰: Lazy Class
جایگاه : ErrorHandler.java
توضیحات : این کلاس فقط یک متد printError دارد و اصلاً نیازی به وجود داشتن یک کلاس ندارد. تمامی این کارها می‌توان با یک کلاس Utility یا enum انجام داد.


مشکل ۱۱: Duplicate Code
جایگاه : CodeGenerator.java – متدهای add, sub, mult
توضیحات : این متدها منطق مشابهی دارند و فقط در نوع Operation متفاوت هستند.


مشکل ۱۲: Incomplete Library Class
جایگاه : LexicalAnalyzerFacade.getNextToken()
توضیحات : این facade فقط یک متد getNextToken() را پوشش می‌دهد و interface‌های محدودی را فراهم می‌کند.

### عملکرد پلاگین formatter، چگونگی کمک‌رسانی آن و رابطه‌اش با بازآرایی کد

پلاگین formatter یک ابزار برای قالب‌‌‌‌‌بندی خودکار کدها در پروژه‌های Maven است. این پلاگین معمولاً بر پایه قالب‌بندی Eclipse عمل می‌کند و می‌تواند فایل‌های Java، XML، HTML، CSS و ... را طبق استاندارد مشخص‌شده مرتب‌سازی و فرمت کند.

**چه کاری انجام می‌دهد؟**
- کدها را طبق یک الگوی مشخص و یکنواخت قالب‌بندی می‌کند.
- می‌تواند در طول فرآیند build اجرا شود یا به‌صورت دستی فراخوانی شود.
- فایل‌هایی با فرمت غیراستاندارد را اصلاح می‌کند تا از نظر ظاهری هماهنگ شوند.

**چرا مفید است؟**
- خوانایی کد را افزایش می‌دهد و مرور آن را برای سایر برنامه‌نویسان ساده‌تر می‌کند.
- از اختلاف‌های بی‌مورد در سیستم کنترل نسخه (مانند Git) جلوگیری می‌کند.
- باعث می‌شود تیم توسعه دارای یک سبک نوشتاری یکپارچه باشد، حتی اگر افراد مختلف روی پروژه کار کنند.

پلاگین formatter به طور مستقیم در بازآرایی کد دخالت نمی‌کند، اما نقش بسیار مهم و مکملی در کنار آن ایفا می‌کند. زمانی که برنامه‌نویس کد را بازآرایی می‌کند (مثلا متدی را به کلاس دیگری منتقل می‌کند، متغیرها را با نام‌های بهتر جایگزین می‌کند یا بلوک‌های منطقی را ساده‌سازی می‌نماید) احتمال دارد نظم ظاهری کد به هم بریزد. در چنین شرایطی، استفاده از پلاگین formatter باعث می‌شود که ظاهر کد به حالت استاندارد و یکنواخت بازگردد.

این پلاگین با اعمال قواعد قالب‌بندی از پیش تعیین‌شده (مانند تنظیم تورفتگی، مرتب‌سازی importها و...)، ظاهر کد را مرتب می‌کند و در نتیجه خوانایی آن را بهبود می‌بخشد. این موضوع به‌ویژه در تیم‌های توسعه‌ای که چندین برنامه‌نویس با سبک‌های نوشتاری مختلف روی یک پروژه کار می‌کنند، اهمیت ویژه‌ای دارد. استفاده از پلاگین formatter باعث می‌شود که کد نهایی، حتی پس از انجام بازآرایی‌های پیچیده، همچنان ظاهری تمیز، قابل‌خواندن و منسجم داشته باشد. علاوه بر این، قالب‌بندی یکنواخت کد باعث کاهش اختلاف‌های ظاهری غیرضروری در سیستم‌های کنترل نسخه مثل Git می‌شود و تمرکز توسعه‌دهنده‌ها را بر تغییرات منطقی و واقعی نگه می‌دارد، نه اختلاف‌های ظاهری بی‌اهمیت.

در مجموع، پلاگین formatter بازآرایی منطقی کد را انجام نمی‌دهد، اما به عنوان یک ابزار مکمل پس از بازآرایی، نقش مهمی در حفظ زیبایی ظاهری و هماهنگی ساختاری کد ایفا می‌کند.

پیاده‌سازی و اجرای موفق formatter در کد
<img width="869" alt="image" src="https://github.com/user-attachments/assets/08a2965c-44cb-4694-ae01-f65735345ee7" />


پایان.
