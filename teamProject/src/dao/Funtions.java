package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import vo.RecommandVO;

public class Funtions {

	private WebDriver webDriver;
	public static final String WEB_DRIVER_PATH = "C:/chromedriver.exe";
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	Scanner sc = new Scanner(System.in);

	String camfitUrl = "https://camfit.co.kr/search/result";
	int number = 0, choice = 0;
	String bigLocationUrl = "", smallLocationUrl = "";
	String campTypeUrl = "", locationTypeUrl = "";
	String choicedBigLocation = "", temp2 = "";

	WebDriver driver = webConnection();

	// ����̹� ����
	public WebDriver webConnection() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		WebDriver driver = webDriver;
		driver = new ChromeDriver();
		driver.get(camfitUrl);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			;
		}
		return driver;
	}

	// ================================���� ����================================
	public void locationSelect() {
		ArrayList<String> bigLocation = null;// ū ������ �̸��� ����
		String bigLocationList = "", smallLocationList = "";// ������� �ѹ��� ������ ����
		driver.findElement(By.cssSelector("div.right-input input.jRXicD")).click();// ������ Ŭ��

		// ū���� ��ҵ� ArrayList ����ֱ�
		bigLocation = new ArrayList<>();

		// ū ���� ��� �̰� ����Ҷ� �� ������ ����
		for (WebElement webElement : driver.findElements(By.cssSelector("li.jAJMLG p.typography-regular"))) {
			bigLocation.add(webElement.getText());
		}

		for (int i = 0; i < bigLocation.size(); i++) {
			bigLocationList += i + 1 + ". " + bigLocation.get(i) + "\n";
		}

		// ����ڿ��� ū���� �Է�(����) �ޱ�
		bigLocationList += "���ϴ� ������ ��ȣ�� �Է����ּ���";
		System.out.println(bigLocationList);
		choice = sc.nextInt();
		System.out.println(bigLocation.get(choice - 1) + " üũ");

		// ����ڿ��� ���� ��ȣ, ������ ��ȣ Ŭ��
		choicedBigLocation = "ul.sc-jGprRt>li:nth-child(" + choice + ")";
		driver.findElement(By.cssSelector(choicedBigLocation)).click();

		// �������� ��� (102), ��õ (9) �̷� ������ �������µ� ���⼭ ������ ����
		String bigLocationName = bigLocation.get(choice - 1).substring(0, bigLocation.get(choice - 1).indexOf(" "));

		// �ּҿ� ���� ���
		bigLocationUrl += "?city=" + bigLocationName;
		// ����, ������ ��� �ش� ����Ʈ url���� �˻� ������ Ǯ�����̹Ƿ� �ش� ��쿡 ���� �������� �� �ٿ��� ������
		if (bigLocationName.equals("����")) {
			bigLocationUrl += "Ư����ġ��";
		}
		if (bigLocationName.equals("����")) {
			bigLocationUrl += "Ư����ġ��";
		}

		// =======================���� ���� Ŭ���ϱ� (��, 0 �Է��ϸ鼱�ÿϷ�)====================
		// ���� �������� ��Ƶ� Arraylist ����
		ArrayList<String> smallLocation = new ArrayList<>();
		for (WebElement webElement : driver.findElements(By.cssSelector("ul.sc-hAsxaJ li.jAbgnw"))) {
			smallLocation.add(webElement.getText());
		}
		// �� ������ ��� �� �� �������� �������� ����� �ǰ� �ߴµ�, �� �κ��� ������ ���� ������ִ� �۾�.
		number = 0;
		for (number = 0; number < smallLocation.size(); number++) {
			smallLocationList += number + 1 + ". " + smallLocation.get(number).replace("\n", "") + "\n";
		}
		smallLocationList += "0. ���ÿϷ�\n���ϴ� ������ ��ȣ�� �Է����ּ���";
		System.out.println(smallLocationList);

		// ����
		temp2 = "";
		String smallLocationNames = "";// ������ ����
		while (true) {
//			3�� �̻� �Է����� �� ����
			choice = sc.nextInt();
			if (choice == 0) {// 0�Է� Ż��
				break;
			}
			String smallLocationName1 = smallLocation.get(choice - 1).replace("\r", "").replace("\n", "");
			// �������� ��ȣ �и�
			String smallLocationName = smallLocationName1.substring(0, smallLocationName1.indexOf("("));
			// -1�� �ƴ϶�� ���� �ش� ���ڰ� temp2�� �̹� ���� ��
			if (temp2.indexOf(choice + "") != -1) {// üũ ����
				// choice�� temp2���� ����� �ε����ִ���
				int index = temp2.indexOf(choice + "");
				// �ش� �ε����� �ִ� ���ڸ� ���ڿ��� �ٲ�
				temp2 = temp2.replace(temp2.charAt(index) + "", "");
				// ������������ ,�������� �ִٸ� ,�������� ���ڿ���
				if (smallLocationNames.contains("," + smallLocationName)) {
					smallLocationNames = smallLocationNames.replace("," + smallLocationName, "");
				} else {
					// ������������ �������� �ִٸ� �������� ���ڿ���
					smallLocationNames = smallLocationNames.replace(smallLocationName, "");
				}
				System.out.println(smallLocationName + " üũ ����");
			} else {// üũ �Ǿ��� ��
				// ���� �ϳ��� �ִٸ� ,�� �ٿ���
				if (temp2.length() + 1 == 4) {
					System.out.println("�ִ� 3������ ������ �� �ֽ��ϴ�");
					continue;
				}
				if (temp2.length() > 0) {
					smallLocationNames += ",";
				}
				// ���������� ����
				smallLocationNames += smallLocationName;
				// ��ȣ����
				temp2 += choice;
				System.out.println(smallLocationName + " üũ");
			}
			// ���� Ŭ��
			choicedBigLocation = "ul.fuIZQM>li:nth-child(" + choice + ")";
			driver.findElement(By.cssSelector(choicedBigLocation)).click();
			if (temp2.length() == 0) {// temp2�� 0�̶�� ���������� �翬�� 0
				smallLocationNames = "";
			}
		}
		if (temp2.length() > 0) {// �������� �ϳ��� �Է��ߴٸ� �ּ� ���Ŀ� ���� ��� �ۼ�
			smallLocationUrl += "&majors=";
		}
		// �ּ� �����̶� ������ ���� ��ħ
		smallLocationUrl += smallLocationNames;

		driver.findElement(By.cssSelector("ul.gSSvYn")).click();// �ݱ� Ŭ��
	}

	// ==========================ķ�� ���� ����==========================================
	public void campTypeSelect() {
		String campTypeList = "", campTypes = "";
		// �ּ�Ÿ���� ���ڿ��� �޾Ƴ���
		String[] engTypes = { "autoCamping", "glamping", "caravan", "pension", "bungalow", "carCamping" };
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			;
		}

		// ķ������ Ŭ��
		driver.findElement(By.cssSelector("div.swiper-slide span.dlthMI")).click();

		ArrayList<String> type = new ArrayList<>();

		// ķ������ ��� ��������
		for (WebElement types : driver.findElements(By.cssSelector("div.sc-fIavCj"))) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				;
			}
			String getType = types.getText();
			type.add(getType);
		}
		// ������
		for (int i = 0; i < type.size(); i++) {
			campTypeList += i + 1 + ". " + type.get(i) + "\n";
		}
		campTypeList += "0. ���ÿϷ�\n���ϴ� ķ�� ������ �������ּ���";
		System.out.println(campTypeList);

		// Ŭ��
		temp2 = "";
		campTypes = "";
		// ķ������ ���ý� ��ưŬ�� �ߺ�����
		while (true) {
			choice = sc.nextInt();
			if (choice == 0) {
				break;
			}

			// ū ���� ��ư Ŭ�� (�ڽ��±׿� ������ ��ȣ�� �־��ش�.)
			temp2 = "div.MuiPaper-root div:nth-child(" + (choice + 1) + ")";
			driver.findElement(By.cssSelector(temp2)).click();

			// ����ڰ� �Է��� ��ȣ�� �ߺ��� ��ȣ���,
			if (temp2.indexOf(choice + "") != -1) {// üũ ����
				int index = temp2.indexOf(choice + "");
				// �Է��ߴ� ��ȣ�� ����ִ� temp2�� �Է��ߴ� ��ȣ�� �����ش�.
				temp2 = temp2.replace(temp2.charAt(index) + "", "");
				// ������ ķ�������߿� ������ ������ ���ڿ��� �ٲ��ش�.(������ �Է� �Ǿ��� ��� ','�� �پ� ������ ������ ���� �����ؼ� �����ش�
				if (campTypes.contains("," + engTypes[choice - 1])) {
					campTypes = campTypes.replace("," + engTypes[choice - 1], "");
				} else {// ','���� ���
					campTypes = campTypes.replace(engTypes[choice - 1], "");
				}
				System.out.println(type.get(choice - 1) + " üũ ����");
			}
//				���� : result?city=���&locationTypes=ocean,mountain,valley&majors=����%2C���ν�&types=autoCamping

			// ���� ����� ��ȣ�� �ߺ����� �ʾҰ�,
			else {
				// ķ��Ÿ���� ���������
				if (temp2.length() > 0) {
					// �ּҿ� "," �߰����ش�.
					campTypes += ",";
				}
				// engTypes�� ��� ���� �ҷ��� ����� �ٲ��ش�.
				campTypes += engTypes[choice - 1];
				// (�ߺ�Ȯ�ο� count ���ֱ�)
				temp2 += choice;
				System.out.println(type.get(choice - 1) + " üũ");
			}
			// temp2�� ���̰� 0�̶�� campTypes ���ڿ��� �ʱ�ȭ
			if (temp2.length() == 0) {
				campTypes = "";
			}
			if (temp2.length() == type.size()) {
				break;
			}
		}
		// ķ��Ÿ���� �����ּ�
		if (temp2.length() > 0) {
			campTypeUrl += "&types=";
		}
		campTypeUrl += campTypes;

//		�ݱ� Ŭ��
		driver.findElement(By.cssSelector("div.MuiPaper-root div.dqOnwu svg")).click();
	}

	// ==========================ȯ�� ���� ����============================
	public void locationTypeSelect() {
		String choicedBigLocation = "", locationTypes = "", locationTypeList = "";
//		�ּ�â���� ��,���...�̷��� �����°� �ƴ϶� ����� ������ ������ ������� ���� �迭
		String[] engLocationType = { "ocean", "mountain", "forest", "river", "lake", "valley", "island", "flat",
				"etc" };
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			;
		}

		// ȯ�� Ŭ��
		driver.findElement(By.cssSelector("div.swiper-slide-next span.dlthMI")).click();
		ArrayList<String> nature = new ArrayList<>(); // ȯ���ҵ� ���� ArrayList
		// ȯ�� ��ҵ� �ҷ�����
		for (WebElement types : driver.findElements(By.cssSelector("div.fLWjSa"))) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				;
			}
			String getType = types.getText();
			nature.add(getType); // nature������ ȯ���ҵ� ���
		}
//		�̾ƿ� ȯ���ҷ� ����ڿ��� ������ ��Ϻ����ֱ�
		for (int i = 0; i < nature.size(); i++) {
			locationTypeList += i + 1 + ". " + nature.get(i) + "\n";// ������� ��� ���
		}
		locationTypeList += "0. ���ÿϷ�\n���ϴ� ȯ�������� ��ȣ�� �����ϼ���.";
		System.out.println(locationTypeList);

		temp2 = "";
		locationTypes = "";// ���ڿ��� �ʱ�ȭ �Ǿ������� �ϴ� �ʱ�ȭ
		while (true) {
			choice = sc.nextInt(); // ����ڰ� �Է��� ��� ��ȣ
			if (choice == 0) { // 0������ ���ÿϷ�
				break;
			}
			choicedBigLocation = "div.MuiPaper-root div:nth-child(" + (choice + 1) + ")"; // ȯ���ϵ� ��)��,�ٴ�,��� ���� ����
			driver.findElement(By.cssSelector(choicedBigLocation)).click(); // ������ ��� Ŭ��

			if (temp2.indexOf(choice + "") != -1) { // ����ڰ� �Է��� ȯ���Ұ� �̹� �ִٸ� �Ʒ� �ڵ� ����(üũ����)
				int index = temp2.indexOf(choice + "");// �Է��� ��ȣ�� �ִ� �ε��� ã��
				temp2 = temp2.replace(temp2.charAt(index) + "", "");// �̹� �ִ� ��ȣ�� ������
				if (locationTypes.contains("," + engLocationType[choice - 1])) {// ���ڿ��� ",mountain"�̷��� �Ǿ����� ���
					locationTypes = locationTypes.replace("," + engLocationType[choice - 1], "");// mountain�� ����� ','��
																									// ���� ������ �װ͵� ���� ������
				} else {// �ƴ϶�� �Ŵ� ��ǥ�� ���°Ŵϱ�
					locationTypes = locationTypes.replace(engLocationType[choice - 1], "");// �׳� ���ڸ� ������
				}
				System.out.println(nature.get(choice - 1) + " üũ ����");
			} else {// ����ڰ� ���ο� ��ȣ�� �Է��� ���
				if (temp2.length() > 0) {// ���̰� 1�̻��� ��� ���õ� ȯ���� �ִٴ� �Ŵϱ�
					locationTypes += ",";// ��ǥ ������
				}
				locationTypes += engLocationType[choice - 1];// ���õ� ȯ�濡 ����
				temp2 += choice;// ��� �Է� �Ǿ����� Ȯ���ϱ� ���� temp2�� �Է��� ��ȣ ����
				System.out.println(nature.get(choice - 1) + " üũ");
			}
			if (temp2.length() == 0) {// temp2�� ���̰� 0�̶�� �Ŵ�
				locationTypes = "";// locationTypes�� ���ڿ��̿��� �Ѵ�.(Ȥ�� �����ۼ�)
			}
			if (temp2.length() == nature.size()) {
				break;
			}

		}
		driver.findElement(By.cssSelector("div.MuiPaper-root button span")).click();
		if (temp2.length() > 0) {// 1�̻� �ִٴ� ���� ����ڰ� ���� ������ �Ŵϱ�
			locationTypeUrl += "&locationTypes=";// �ּ� ���Ŀ� �´� "&locationTypes=" �� �Ǿտ� ������
		}
		locationTypeUrl += locationTypes;// "&locationTypes="�̰Ͱ� ������ ���� �ߴ� ȯ���� ���� �̸��� ��ħ
	}

	// ========================���� ������ �ּ� ��ħ=============================
	public String getAddress() {
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("headless");
		JdbcDAO jdbcDAO = new JdbcDAO();
		List<WebElement> campInfo = null;

//		�������� ��� ķ������ ���� ���� ����(�ϰ�ó��)
		String campList = "", basics = "", additionals = "", leisures = "";
		Method method = new Method();

		driver.close();
//		���� ����ڰ� �Է��� ������ ���� �ּҸ� ����
		String sumUrl = camfitUrl + bigLocationUrl + locationTypeUrl + smallLocationUrl + campTypeUrl;
//		���� �ּҷ� ����
		driver = new ChromeDriver();
		driver.get(sumUrl);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			;
		}

//					ķ������ ���� ����(�ּ�, �̸�, ķ������, ȯ��)
		ArrayList<String> campAddress = new ArrayList<String>();
		ArrayList<String> campingName = new ArrayList<String>();
		ArrayList<String> campType = new ArrayList<String>();
		ArrayList<String> environment = new ArrayList<String>();

//					�������� ���� url
		List<WebElement> campUrls = driver.findElements(By.cssSelector("div a"));
//					�� ķ���Ұ��� �̾����� url����
		ArrayList<String> campUrl = new ArrayList<String>();
//					�������� ���� campUrls�� href�� �ּҸ� �̾ƿ� ����
		for (WebElement webElement : campUrls) {
			campUrl.add(webElement.getAttribute("href"));//
		}
		
		driver.close();
//			url������ ���� �� ����
		number = 0;
		for (String url : campUrl) {
			if (number == 5) {
				break;
			}
//			����̹� ���� �� �� ķ���������� �ִ� �ּҷ� �̵�
			driver = new ChromeDriver();
			driver.get(url);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				;
			}
//		ķ���� �̸�
			campingName.add(driver.findElement(By.cssSelector("div.gFlZWK p.margin")).getText());
//			ķ������
			campType.add(driver.findElement(By.xpath("//*[@id=\"root\"]/div/section/div/div[3]/div[2]/ul/li[1]/p"))
					.getText());
//			ķ��ȯ��
			environment.add(driver.findElement(By.xpath("//*[@id=\"root\"]/div/section/div/div[3]/div[2]/ul/li[2]/p"))
					.getText());

//						ķ���� ����(��ǥ��ȭ, �ּ�)
			campInfo = driver.findElements(By.cssSelector("li.withBtn p"));
//						��ǥ��ȣ
			String generalDirectoryNumber = campInfo.get(0).getText();
//						�ּ�
			campAddress.add(campInfo.get(1).getText());

//						ķ���� �⺻ �ü� ��������
			basics = "";
			List<WebElement> basicService = driver
					.findElements(By.cssSelector("div.sc-bBXxYQ div:nth-child(1) div.sc-grVGCS li"));
			for (WebElement webElement : basicService) {
				basics += (webElement.getText() + " ");
			}
			if (basics.length() == 0) {
				basics = "����";
			}
//						ķ���� �ΰ� �ü� ��������
			additionals = "";
			List<WebElement> additionalService = driver
					.findElements(By.cssSelector("div.sc-bBXxYQ div:nth-child(2) div.sc-grVGCS li"));
			for (WebElement webElement : additionalService) {
				additionals += (webElement.getText() + " ");
			}
			if (additionals.length() == 0) {
				additionals = "����";
			}
//						ķ���� ���� �ü� ��������
			leisures = "";
			List<WebElement> leisureService = driver
					.findElements(By.cssSelector("div.sc-bBXxYQ div:nth-child(3) div.sc-grVGCS li"));
			for (WebElement webElement : leisureService) {
				leisures += (webElement.getText() + " ");
			}
			if (leisures.length() == 0) {
				leisures = "����";
			}
//						ķ���� �̸�,��ǥ ��ȭ,�ü��� ���� ���
//						��Ͽ� ���� �ο�
			number++;
			campList += number + ". " + driver.findElement(By.className("margin")).getText() + "\n" + "��ǥ��ȭ : "
					+ generalDirectoryNumber + "\n" + "�⺻�ü� : " + basics + "\n" + "�ΰ��ü� : " + additionals + "\n"
					+ "�����ü� : " + leisures + "\n";
			campList += "------------------------------------\n";
			driver.close();
		}
		if (campList.length() == 0) {
			System.out.println("�˻� ����� �����ϴ�");
			driver.close();
			return null;
		}
		System.out.println(campList + "���ϴ� ķ������ ��ȣ�� �Է��Ͻø� ��ó ������ ã�� �帳�ϴ�");
		choice = sc.nextInt();
		System.out.println(campAddress.get(choice - 1) + " ��ó ������ �˻��մϴ�");

//		�����ͺ��̽��� �Ѱ��� ��ü
		RecommandVO recommandVO = new RecommandVO();
		recommandVO.setCampAddress(campAddress.get(choice - 1));
		recommandVO.setCampingName(campingName.get(choice - 1));
		recommandVO.setCampType(campType.get(choice - 1));
		recommandVO.setEnvironment(environment.get(choice - 1));

//		���ô��� ķ���� �����ͺ��̽��� ����
		jdbcDAO.inputBestCamp(recommandVO);

		return method.removeAddress(campAddress.get(choice - 1));// ķ���� �ּ� ����
	}

}
