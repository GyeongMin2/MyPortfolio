## Java Playground

### Java Playground Project 소개
>이 프로젝트는 Java로 구현된 리소스모니터 및 저수준 소켓통신 구현 프로젝트입니다.

### 주요 기능1
- 리소스모니터

### 주요 기능2
- 저수준 소켓통신 (server,client)

### 주요 소스코드

#### 1-1. ResourceMonitor.java
- 리소스모니터 구현
- 해당 파일을 컴파일후 crontab에 등록하여 실행
```java
public class ResourceMonitor {

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("./resourceLog.txt", true);) {
            OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String currentDateTime = formatter.format(new Date());
            
            double totalCpuUsage = 0;
            double totalMemorySizeGB, usedMemorySizeGB, freeMemorySizeGB;
            double rxMB = 0, txMB = 0;

            try {
                Process process = Runtime.getRuntime().exec("top -bn1");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("%Cpu(s):")) {
                        String[] cpuUsageParts = line.split(",");
                        String userCpu = cpuUsageParts[0].split(":")[1].trim();
                        String systemCpu = cpuUsageParts[1].trim();

                        double userCpuValue = Double.parseDouble(userCpu.split(" ")[0]);
                        double systemCpuValue = Double.parseDouble(systemCpu.split(" ")[0]);
                        totalCpuUsage = userCpuValue + systemCpuValue;

                        break;
                    }
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            long totalMemorySize = osBean.getTotalPhysicalMemorySize();
            long freeMemorySize = osBean.getFreePhysicalMemorySize();
            long usedMemorySize = totalMemorySize - freeMemorySize;

            totalMemorySizeGB = (double) totalMemorySize / (1024 * 1024 * 1024);
            usedMemorySizeGB = (double) usedMemorySize / (1024 * 1024 * 1024);
            freeMemorySizeGB = (double) freeMemorySize / (1024 * 1024 * 1024);

            try {
                Process process = Runtime.getRuntime().exec("ip -s link");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("RX:")) {
                        String[] rxLineParts = reader.readLine().trim().split("\\s+");
                        long rxBytes = Long.parseLong(rxLineParts[0]);
                        rxMB = (double) rxBytes / (1024 * 1024);
                    } else if (line.trim().startsWith("TX:")) {
                        String[] txLineParts = reader.readLine().trim().split("\\s+");
                        long txBytes = Long.parseLong(txLineParts[0]);
                        txMB = (double) txBytes / (1024 * 1024);
                    }
                }

                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            writer.write(String.format("%s\tCPU: %.2f%%\tTotal Memory: %.2f GB\tUsed Memory: %.2f GB\tFree Memory: %.2f GB\tRX: %.2f MB\tTX: %.2f MB\n",
                    currentDateTime, totalCpuUsage, totalMemorySizeGB, usedMemorySizeGB, freeMemorySizeGB, rxMB, txMB));

            System.out.println("로그가 resourceLog.txt 파일에 저장되었습니다.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
#### 2-1. SocketServer.java
- 저수준 소켓통신 server 구현
```java
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main{
	public static void main(String[] args){
		JdbcTest jdbcTest = new JdbcTest();
		String filePath = "./log.txt"; //logFile path
		try(ServerSocket serverSocket = new ServerSocket(10004);){ //portNum
			while (true) {
				System.out.println("socket ServerStart");
				Socket clientSocket = serverSocket.accept();
				System.out.println("client connected");

				//client ipAddress
				InetAddress clientIpAdd = clientSocket.getInetAddress();

				//client connection processing
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
				String id = input.readLine();

				//connect with DB
				Connection conn = jdbcTest.getConnection();
				if (conn != null) {
					try {
						//query
						String sql = "SELECT UserName FROM socketTestDb.test_tbl WHERE UserId = ?;";
						PreparedStatement prstmt = conn.prepareStatement(sql);
						prstmt.setString(1, id);
						ResultSet rs = prstmt.executeQuery();

						try (FileWriter writer = new FileWriter(filePath, true)) {
							//create file 
							File logFile = new File(filePath);
							if (logFile.createNewFile()) {
								System.out.println("create log file" + logFile.getName());
							} else {
								System.out.println("logFile already created");
							}

							//timestamp
							LocalDateTime currentTime = LocalDateTime.now();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
							String nowTime = currentTime.format(formatter);

							if (rs.next()) {
								String userName = rs.getString("UserName");
								output.println(userName);
								writer.write("Time : "+nowTime+", Client Ip Address : " + clientIpAdd + ", request : " + id + ", response : " + userName + "\n");
							} else {
								output.println("no user found");
								writer.write("Time : "+nowTime+", Client Ip Address : " + clientIpAdd + ", request : " + id + ", response : user not found\n");
							}
						} catch (IOException e) {
							System.out.println("create log file err" + e.getMessage());
						}
					} catch (Exception e) {
						System.out.println("dbc err :" + e.getMessage());
						e.printStackTrace();
					}
				} else {
					System.out.println("connection already closed");
				}
			}
		} catch (Exception e) {
			System.out.println("socket Server Exception" + e.getMessage());
		}
	}
}
```

#### 2-2. Client.java
- 저수준 소켓통신 client 구현
```java
public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server address
        int port = 10004; // Server port
        String userId = "user123"; // User ID to test

        try (Socket socket = new Socket(serverAddress, port)) {
            // Create input and output streams for communication with the server
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send the user ID to the server
            output.println(userId);

            // Read the response from the server
            String response = input.readLine();
            System.out.println("Response from server: " + response);
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
```

### 향후 개선 및 추가 할 기능
- 소켓통신 예외처리 강화
- 소켓통신 속도 개선
