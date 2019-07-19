开放平台V2版本接口文档

# 1 合同接口

## 1.1 创建合同

### 1.1.1 创建合同草稿

**描述：**根据业务分类配置创建合同草稿，业务分类在契约锁公有云中配置。

> **业务分类配置介绍：**
>
> **签署方：**
>
> （1）签署方预设时，用户传入的签署方与业务分类配置的签署方必须完全一致（数量、类型、顺序均匹配），此时会使用业务分类配置的签署流程、签署位置、印章等（发起方除外，发起方的签署流程下面介绍）。
>
> （2）签署方非预设时，以参数中传入的签署方为准（发起方除外）。
>
> **发起方签署流程：**
>
> （1）发起方流程预设时，发起方使用预设的签署流程、签署位置、印章等。
>
> （2）发起方流程非预设时，以参数中传入的签署方为准。如果此时发起方签署公章节点未设置公章或签署人，将会使用业务分类中配置的公章和签署人。
>
> **文件模板：**
>
> 发起合同时，必须保证合文件模板中发起方的必填参数均已填写完成。

**请求地址：**/v2/contract/draft

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数           | 类型                      | 必须 | 描述                                                         |
| -------------- | ------------------------- | ---- | ------------------------------------------------------------ |
| id             | String                    | 否   | 合同ID（接口返回值）                                         |
| bizId          | String                    | 否   | 业务ID；<br />一个合同对应一个bizId，不能重复                |
| subject        | String                    | 是   | 合同主题（合同名称）                                         |
| description    | String                    | 否   | 合同描述                                                     |
| sn             | String                    | 否   | 合同编号；可由用户传入，<br />也可由契约锁自动生成           |
| expireTime     | String                    | 否   | 合同过期时间；<br />格式为yyyy-MM-dd HH:mm:ss，<br />默认过期时间为业务分类中配置的时间 |
| ordinal        | Boolean                   | 否   | 是否顺序签署；默认为true                                     |
| send           | Boolean                   | 否   | 是否发起合同；<br />发起合同后不能再进行添加文档、<br />添加签署方等操作 |
| category       | Category                  | 否   | 业务分类；默认为“默认业务分类”                               |
| creator        | User                      | 否   | 创建人；默认为虚拟用户，<br />创建人必须已经加入对接方的公司 |
| status         | String                    | 否   | 合同状态（接口返回值）：DRAFT（草稿）<br />RECALLED（已撤回），SIGNING（签署中），REJECTED（已退回），COMPLETE（已完成），EXPIRED（已过期），FILLING（拟定中），INVALIDING（作废中），INVALIDED（已作废） |
| signatories    | List&lt;Signatory&gt;     | 否   | 签署方；签署合同的公司/个人；<br />发起合同时必传；          |
| templateParams | List&lt;TemplateParam&gt; | 否   | 模板参数，用于文件模板的填参                                 |
| documents      | List&lt;Document&gt;      | 否   | 合同文档（接口返回值）                                       |

Category（业务分类）：

| 参数 | 类型   | 必须 | 描述                                                         |
| ---- | ------ | ---- | ------------------------------------------------------------ |
| id   | String | 否   | 业务分类ID                                                   |
| name | String | 否   | 业务分类名称；<br />如果id为空时，使用name来确定业务分类，<br />需要保证name对应的业务分类唯一 |

User（个人用户）：

| 参数        | 类型   | 必须 | 描述                                                         |
| ----------- | ------ | ---- | ------------------------------------------------------------ |
| contact     | String | 是   | 联系方式                                                     |
| contactType | String | 是   | 联系类型：MOBILE（手机号），EMAIL（邮箱），EMPLOYEEID（员工ID），NUMBER（员工编号） |

Signatory（签署方）：

| 参数        | 类型                  | 必须 | 描述                                                |
| ----------- | --------------------- | ---- | --------------------------------------------------- |
| id          | String                | 否   | 签署方ID（接口返回值）                              |
| tenantType  | String                | 是   | 签署方类型：COMPANY（公司），<br />PERSONAL（个人） |
| tenantName  | String                | 是   | 签署方名称                                          |
| receiver    | User                  | 否   | 接收人（经办人）；个人时必须                        |
| serialNo    | Integer               | 是   | 签署顺序                                            |
| actions     | List&lt;Action&gt;    | 否   | 签署动作（签署流程）                                |
| attachments | List&lt;Attchment&gt; | 否   | 附件要求；用于指定用户签署时上传附件                |

Action（签署动作/签署流程）：

| 参数      | 类型             | 必须 | 描述                                                         |
| --------- | ---------------- | ---- | ------------------------------------------------------------ |
| id        | String           | 否   | 签署节点ID（接口返回值）                                     |
| type      | String           | 是   | 类型：COMPANY（签署公章），OPERATOR（经办人签署）<br />PERSONAL（个人签名），LP（法人签署），AUDIT（审批） |
| name      | String           | 否   | 名称                                                         |
| serialNo  | Integer          | 是   | 执行顺序，从1开始                                            |
| sealId    | String           | 否   | 印章ID；用于指定签署时所有印章                               |
| operators | List&lt;User&gt; | 否   | 操作人；用于指定公章签署人，法人签署和经办人签署时无需指定   |

Attchment（附件要求）：

| 参数     | 类型    | 必须 | 描述                      |
| -------- | ------- | ---- | ------------------------- |
| id       | String  | 否   | 附件ID（接口返回值）      |
| title    | String  | 是   | 名称                      |
| required | Boolean | 否   | 是否必须；默认为false     |
| needSign | Boolean | 否   | 是否需要签署；默认为false |

Document（合同文档）：

| 参数       | 类型    | 描述                                |
| ---------- | ------- | ----------------------------------- |
| id         | String  | 附件ID                              |
| title      | String  | 名称                                |
| pageCount  | Integer | 是否必须；默认为false               |
| createTime | String  | 创建时间；格式为yyyy-MM-dd HH:mm:ss |

**返回参数：**

| 参数    | 类型    | 描述                         |
| ------- | ------- | ---------------------------- |
| code    | Integer | 响应码                       |
| message | String  | 响应消息                     |
| result  | Object  | 返回数据；格式与请求参数一致 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1102   | REPEATING SIGNATORY，签署方重复                              |
| 1103   | ACTION REQUIRED，缺少签署节点；签署方为公司时，必须保证其下的签署流程<br />不为空，签署流程可以由参数传入，也可以使用业务分类中配置的签署流程。 |
| 1105   | ACTION OPERATORS REQUIRED，缺少操作人；<br />签署方为公司时，其下的“审批”、“签署”节点必须设置操作人。 |
| 1111   | OPERATOR REQUIRED，缺少经办人；<br />存在经办人节点时，必须设置经办人。 |
| 1112   | DUPLICATE BIZID，重复的bizId；                               |
| 1113   | SIGNATORY REQUIRED，缺少签署方                               |
| 1201   | SEAL NOT FOUND，找不到印章                                   |
| 1203   | INVALID SEAL STATUS，印章不是正常状态                        |
| 1301   | CATEGORY CONFIG NOT MATCH，业务分类配置的签署方数量/类型<br />与参数中的签署方数量/类型不匹配 |
| 1302   | CATEGORY CONFIG ERROR，业务分类配置数据错误；<br />可能原因有：业务分类中配置的操作人已离职，或其他错误。 |
| 1401   | DOCUMENT REQUIRED，缺少合同文档；发起合同时必须有合同文档    |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 合同基本参数
Contract contract = new Contract();
contract.setSubject("测试合同");
contract.setCategory(new Category("个人-平台-预设签署方-默认流程"));
contract.setBizId("");
contract.setSend(false);
// 个人
Signatory signatory1 = new Signatory();
signatory1.setTenantName("宋三");
signatory1.setTenantType("PERSONAL");
signatory1.setReceiver(new User("宋三", "13636350111", "MOBILE"));
signatory1.setSerialNo(1);
// 平台方
Signatory signatory2 = new Signatory();
signatory2.setTenantName("测试合同");
signatory2.setTenantType("COMPANY");
signatory2.setReceiver(new User("周一", "13636350000", "MOBILE"));
signatory2.setSerialNo(2);
Action action = new Action("COMPANY", 1);
signatory2.addAction(action);
// 设置签署方
contract.addSignatory(signatory1);
contract.addSignatory(signatory2);
contract.addTemplateParam(new TemplateParam("param1", "value1"));
contract.addTemplateParam(new TemplateParam("param2", "value2"));
// 创建合同
ContractDraftRequest request = new ContractDraftRequest(contract);
String response = sdkClient.service(request);
SdkResponse<Contract> responseObj = JSONUtils.toQysResponse(response, Contract.class);
// 返回结果
if(responseObj.getCode() == 0) {
	Contract result = responseObj.getResult();
	logger.info("创建合同成功，合同ID:{}", result.getId());
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>




### 1.1.2 添加合同文档

#### 1.1.2.1 用文件添加合同文档

**请求地址：**/v2/document/addbyfile

**请求方法：**POST

**请求格式:** multipart/form-data;charset=UTF-8

**请求参数：**

| 参数       | 类型   | 必须 | 描述                                                         |
| ---------- | ------ | ---- | ------------------------------------------------------------ |
| contractId | String | 是   | 合同ID                                                       |
| title      | String | 是   | 名称                                                         |
| file       | File   | 是   | 合同文件                                                     |
| fileSuffix | String | 是   | 文件类型（文件后缀）：<br />doc，docx，pdf，jpeg，png，gif，tiff，html，htm |

**返回参数：**

| 参数    | 类型     | 描述     |
| ------- | -------- | -------- |
| code    | Integer  | 响应码   |
| message | String   | 响应消息 |
| result  | Response | 返回数据 |

Response（返回数据）：

| 参数       | 类型   | 描述       |
| ---------- | ------ | ---------- |
| documentId | String | 合同文档ID |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1101   | INVALID CONTRACT STATUS，合同状态无效；<br />只能为“草稿”状态合同添加文档。 |
| 1403   | INVALID FILE，无效的合同文件；<br />合同文件转换失败，可能是由于文件损坏或者文件类型错误。 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 添加合同文档
DocumentAddByFileRequest request = new DocumentAddByFileRequest(contractId,
				new StreamFile(new FileInputStream("E:/test/NoSign.pdf")), "pdf", "文件一");
String response = sdkClient.service(request);
SdkResponse<DocumentAddResult> responseObj = JSONUtils.toQysResponse(response, DocumentAddResult.class);
if(responseObj.getCode() == 0) {
	DocumentAddResult result = responseObj.getResult();
	logger.info("添加合同文档成功，文档ID:{}", result.getDocumentId());
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

#### 1.1.2.2 用模板添加合同文档

**请求地址：**/v2/document/addbytemplate

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数           | 类型                      | 必须 | 描述                             |
| -------------- | ------------------------- | ---- | -------------------------------- |
| contractId     | String                    | 是   | 合同ID                           |
| title          | String                    | 是   | 名称                             |
| templateId     | String                    | 是   | 模板ID                           |
| templateParams | List&lt;TemplateParam&gt; | 否   | 模板参数；如果是参数模板，则必填 |

TemplateParam（模板参数）：

| 参数  | 类型   | 必须 | 描述     |
| ----- | ------ | ---- | -------- |
| name  | String | 是   | 参数名称 |
| value | String | 是   | 参数值   |

**返回参数：**

| 参数    | 类型     | 描述     |
| ------- | -------- | -------- |
| code    | Integer  | 响应码   |
| message | String   | 响应消息 |
| result  | Response | 返回数据 |

Response（返回数据）：

| 参数       | 类型   | 描述       |
| ---------- | ------ | ---------- |
| documentId | String | 合同文档ID |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 添加合同文档
List<TemplateParam> params = new ArrayList<>();
params.add(new TemplateParam("param1", "val1"));
params.add(new TemplateParam("param2", "val2"));
DocumentAddByTemplateRequest request = new DocumentAddByTemplateRequest(contractId,
		templateId, params, "文件二");
String response = sdkClient.service(request);
SdkResponse<DocumentAddResult> responseObj = JSONUtils.toQysResponse(response, DocumentAddResult.class);
if(responseObj.getCode() == 0) {
	DocumentAddResult result = responseObj.getResult();
	logger.info("添加合同文档成功，文档ID:{}", result.getDocumentId());
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 1.1.3 发起合同

**描述：**调用此接口来发起“草稿”状态的合同。合同发起后，签署方将收到签署通知签署合同。支持在发起合同时指定签署位置。

**请求地址：**/v2/contract/send

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数       | 类型    | 必须 | 描述         |
| ---------- | ------- | ---- | ------------ |
| contractId | String  | 是   | 合同ID       |
| stampers   | Stamper | 否   | 指定签署位置 |

Stamper（签署位置）：

| 参数         | 类型    | 必须 | 描述                                                         |
| ------------ | ------- | ---- | ------------------------------------------------------------ |
| actionId     | String  | 否   | 签署节点ID；公司的签署位置必须                               |
| signatoryId  | String  | 否   | 签署方ID；个人的签署位置必传                                 |
| type         | String  | 是   | 签署类型：<br />COMPANY（公章），PERSONAL（个人签名），<br />LP（法人章），TIMESTAMP（时间戳），ACROSS_PAGE（骑缝章） |
| documentId   | String  | 是   | 合同文档ID                                                   |
| sealId       | String  | 否   | 印章ID（暂未支持）                                           |
| keyword      | String  | 否   | 关键字                                                       |
| keywordIndex | Integer | 否   | 关键字索引：1代表第1个关键字，0代码所有关键字<br />，-1代表倒数第1个关键字；默认为1 |
| page         | Integer | 否   | 坐标页码，0代表所有                                          |
| offsetX      | Decimal | 否   | 横坐标/关键字偏移量                                          |
| offsetY      | Decimal | 否   | 纵坐标/关键字偏移量                                          |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1101   | INVALID CONTRACT STATUS，合同状态无效；<br />只能发起“草稿”状态合同。 |
| 1108   | KEYWORD NOT FOUND，未找到关键字；<br />签署位置中关键字未在文档中找到。 |
| 1109   | SIGN PAGE BEYOND，签署页码超出文档页数；<br />签署位置中的页码超出文档页数。 |
| 1203   | INVALID SEAL STATUS，无效的印章状态                          |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 发起时可以设置签署位置
Stamper stamper = new Stamper();
stamper.setActionId(2589310177048080947L);
stamper.setDocumentId(2589310172379820580L);
stamper.setType("COMPANY");
stamper.setPage(1);
stamper.setOffsetX(0.1);
stamper.setOffsetY(0.1);
Stamper stamper2 = new Stamper();
stamper2.setActionId(2589310177048080947L);
stamper2.setDocumentId(2589310172379820580L);
stamper2.setType("COMPANY");
stamper2.setPage(1);
stamper2.setOffsetX(0.2);
stamper2.setOffsetY(0.1);
List<Stamper> stampers = new ArrayList<>();
stampers.add(stamper);
stampers.add(stamper2);
// 发起合同
ContractSendRequest request = new ContractSendRequest(2589310172899914283L, stampers);
String response = sdkClient.service(request);
SdkResponse<Object> responseObj = JSONUtils.toQysResponse(response);
if(responseObj.getCode() == 0) {
	logger.info("合同发起成功");
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 1.2 签署合同

### 1.2.1 签署公章

**请求地址：**/v2/contract/companysign

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数       | 类型    | 必须 | 描述         |
| ---------- | ------- | ---- | ------------ |
| contractId | String  | 是   | 合同ID       |
| stampers   | Stamper | 否   | 指定签署位置 |

Stamper（签署位置）：

| 参数         | 类型    | 必须 | 描述                                                         |
| ------------ | ------- | ---- | ------------------------------------------------------------ |
| type         | String  | 是   | 签署类型：COMPANY（公章），<br />TIMESTAMP（时间戳），ACROSS_PAGE（骑缝章） |
| documentId   | String  | 是   | 合同文档ID                                                   |
| sealId       | String  | 否   | 印章ID                                                       |
| keyword      | String  | 否   | 关键字                                                       |
| keywordIndex | Integer | 否   | 关键字索引：1代表第1个关键字，<br />0代表所有关键字，-1代表倒数第1个关键字；默认为1 |
| page         | Integer | 否   | 坐标页码，0代表所有                                          |
| offsetX      | Decimal | 否   | 横坐标/关键字偏移量                                          |
| offsetY      | Decimal | 否   | 纵坐标/关键字偏移量                                          |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1106   | INVALID_STAMPER_TYPE，无效的签章类型；签署公章支持的签章类型如下：<br />COMPANY（公章），TIMESTAMP（时间戳），ACROSS_PAGE（骑缝章）。 |
| 1107   | NOT SIGN STEP，未挨到签署；<br />未挨到公司签署公章或者没有签署公章节点。 |
| 1109   | SIGN PAGE BEYOND，签署页码超出文档页数；<br />签署位置中的页码超出文档页数。 |
| 1201   | SEAL NOT FOUND，找不到印章                                   |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 签署公章
SignParam param = new SignParam();
param.setContractId(2589394742435041330L);
ContractSignCompanyRequest request = new ContractSignCompanyRequest(param);
String response = sdkClient.service(request);
SdkResponse<Object> responseObj = JSONUtils.toQysResponse(response);
if(responseObj.getCode() == 0) {
	logger.info("公章签署成功");
} else {
	logger.info("公章签署失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 1.2.2 签署法人章

**请求地址：**/v2/contract/legalpersonsign

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数       | 类型    | 必须 | 描述         |
| ---------- | ------- | ---- | ------------ |
| contractId | String  | 是   | 合同ID       |
| stampers   | Stamper | 否   | 指定签署位置 |

Stamper（签署位置）：

| 参数         | 类型    | 必须 | 描述                                                         |
| ------------ | ------- | ---- | ------------------------------------------------------------ |
| type         | String  | 是   | 签署类型：<br />LP（法人章），TIMESTAMP（时间戳）            |
| documentId   | String  | 是   | 合同文档ID                                                   |
| keyword      | String  | 否   | 关键字                                                       |
| keywordIndex | Integer | 否   | 关键字索引：1代表第1个关键字，<br />0代码所有关键字，-1代表倒数第1个关键字；默认为1 |
| page         | Integer | 否   | 坐标页码，0代表所有                                          |
| offsetX      | Decimal | 否   | 横坐标/关键字偏移量                                          |
| offsetY      | Decimal | 否   | 纵坐标/关键字偏移量                                          |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1106   | INVALID_STAMPER_TYPE，无效的签章类型；签署法人章支持的签章类型如下：<br />LP（法人章），TIMESTAMP（时间戳）。 |
| 1107   | NOT SIGN STEP，未挨到签署；<br />未挨到公司签署法人章或者没有签署法人章节点。 |
| 1109   | SIGN PAGE BEYOND，签署页码超出文档页数；<br />签署位置中的页码超出文档页数。 |
| 1201   | SEAL NOT FOUND，找不到法人章；<br />请确保在契约锁已维护法人章。 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 签署法人章
SignParam param = new SignParam();
param.setContractId(2589394742435041330L);
ContractSignLpRequest request = new ContractSignLpRequest(param);
String response = sdkClient.service(request);
SdkResponse<Object> responseObj = JSONUtils.toQysResponse(response);
if(responseObj.getCode() == 0) {
	logger.info("法人章签署成功");
} else {
	logger.info("法人章签署失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 1.2.3 员工审批

**请求地址：**/v2/contract/employeeaudit

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数       | 类型   | 必须 | 描述         |
| ---------- | ------ | ---- | ------------ |
| contractId | String | 是   | 合同ID       |
| employee   | User   | 是   | 审批人       |
| pass       | Bolean | 是   | 审批是否通过 |
| comment    | String | 否   | 审批意见     |

User（个人用户）：

| 参数        | 类型   | 必须 | 描述                                                         |
| ----------- | ------ | ---- | ------------------------------------------------------------ |
| contact     | String | 是   | 联系方式                                                     |
| contactType | String | 是   | 联系类型：MOBILE（手机号），<br />EMAIL（邮箱），EMPLOYEEID（员工ID） |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1107   | NOT SIGN STEP，未挨到签署；<br />未挨到员工审批，或者没有员工审批节点。 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 员工审批
User user = new User("13636350212", "MOBILE");
ContractAuditRequest request = new ContractAuditRequest(contractId, user, true, "审批通过");
String response = sdkClient.service(request);
SdkResponse<Object> responseObj = JSONUtils.toQysResponse(response);
if(responseObj.getCode() == 0) {
	logger.info("审批成功");
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 1.2.4 签署页面

**请求地址：**/v2/contract/pageurl

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数         | 类型   | 必须 | 描述     |
| ------------ | ------ | ---- | -------- |
| contractId   | String | 是   | 合同ID   |
| user         | User   | 是   | 操作人   |
| callbackPage | String | 否   | 回调页面 |

User（个人用户）：

| 参数        | 类型   | 必须 | 描述                                                         |
| ----------- | ------ | ---- | ------------------------------------------------------------ |
| contact     | String | 是   | 联系方式                                                     |
| contactType | String | 是   | 联系类型：MOBILE（手机号），<br />EMAIL（邮箱），EMPLOYEEID（员工ID），NUMBER（员工编号） |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1110   | NO VIEW PERMISSION，没有查看权限；<br />用户没有权限查看合同。 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 合同页面
ContractPageRequest request = new ContractPageRequest(2589382258757710050L,
				new User("13636350212", "MOBILE"), "");
String response = sdkClient.service(request);
SdkResponse<ContractPageResult> responseObj = JSONUtils.toQysResponse(response, ContractPageResult.class);
if(responseObj.getCode() == 0) {
	ContractPageResult result = responseObj.getResult();
	logger.info("合同页面地址为:{}", result.getPageUrl());
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 1.3 合同催签

**请求地址：**/v2/contract/notice

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数        | 类型   | 必须 | 描述     |
| ----------- | ------ | ---- | -------- |
| contractId  | String | 是   | 合同ID   |
| signatoryId | String | 否   | 签署方ID |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 合同催签
ContractNoticeRequest request = new ContractNoticeRequest(contractId);
String response = sdkClient.service(request);
SdkResponse responseObj = JSONUtils.toQysResponse(response);
if(responseObj.getCode() == 0) {
	logger.info("合同催签成功");
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 1.4 合同详情

**请求地址：**/v2/contract/detail

**请求方法：**GET

**请求参数：**

| 参数       | 类型   | 必须 | 描述   |
| ---------- | ------ | ---- | ------ |
| contractId | String | 是   | 合同ID |

**返回参数：**

| 参数    | 类型     | 描述                             |
| ------- | -------- | -------------------------------- |
| code    | Integer  | 响应码                           |
| message | String   | 响应消息                         |
| result  | Contract | 合同详情，参考创建合同的请求参数 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 合同详情
ContractDetailRequest request = new ContractDetailRequest(contractId);
String response = sdkClient.service(request);
SdkResponse<Contract> responseObj = JSONUtils.toQysResponse(response, Contract.class);
if(responseObj.getCode() == 0) {
	Contract contract = responseObj.getResult();
	logger.info("合同详情查询，合同主题：{}", contract.getSubject());
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 1.5 下载合同

### 1.5.1 下载合同与签署日志

**请求地址：**/v2/contract/download

**请求方法：**GET

**请求参数：**

| 参数       | 类型   | 必须 | 描述   |
| ---------- | ------ | ---- | ------ |
| contractId | String | 是   | 合同ID |

**返回参数：**

合同文件和签署日志的压缩包（.zip）

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 下载合同
ContractDownloadRequest request = new ContractDownloadRequest(contractId);
FileOutputStream fos = new FileOutputStream("E:/test/contract.zip");
sdkClient.download(request, fos);
IOUtils.safeClose(fos);
logger.info("下载合同成功");
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 1.5.2 下载合同文档

**请求地址：**/v2/document/download

**请求方法：**GET

**请求参数：**

| 参数       | 类型   | 必须 | 描述       |
| ---------- | ------ | ---- | ---------- |
| documentId | String | 是   | 合同文档ID |

**返回参数：**

合同文件（.pdf）

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 下载合同文档
DocumentDownloadRequest request = new DocumentDownloadRequest(2586891626797203479L);
FileOutputStream fos = new FileOutputStream("E:/test/doc.pdf");
sdkClient.download(request, fos);
IOUtils.safeClose(fos);
logger.info("下载合同文档成功");
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 1.5.3 下载合同附件

**请求地址：**/v2/attachment/download

**请求方法：**GET

**请求参数：**

| 参数       | 类型   | 必须 | 描述   |
| ---------- | ------ | ---- | ------ |
| contractId | String | 是   | 合同ID |

**返回参数：**

合同附件压缩包（.zip）

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 下载合同附件
AttachmentDownloadRequest request = new AttachmentDownloadRequest(contractId);
FileOutputStream fos = new FileOutputStream("E:/test/attachment.pdf");
sdkClient.download(request, fos);
IOUtils.safeClose(fos);
logger.info("下载附件成功");
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 1.6 撤回/作废合同

**描述：**调用此接口撤回/作废合同。

> 合同是“草稿”状态时，删除合同；
> 合同是“签署中”状态时，撤回合同，合同变为“已撤回”状态；
> 合同是“已完成”状态时，作废合同（作废合同需要所有签署方签署作废文件后，作废完成），同时在作废文件上签署发起方公章。

**请求地址：**/v2/contract/invalid

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数       | 类型    | 必须 | 描述                                                   |
| ---------- | ------- | ---- | ------------------------------------------------------ |
| contractId | String  | 是   | 合同ID                                                 |
| sealId     | String  | 否   | 印章ID；作废合同时<br />发起方签署作废文件时指定的印章 |
| reason     | String  | 否   | 撤回/作废原因                                          |
| deleteDoc  | Boolean | 否   | 作废完成后是否删除合同文件，默认false                  |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1101   | INVALID CONTRACT STATUS，无效的合同状态；以下是支持的合同状态及对应操作：<br />DRAFT（草稿）：删除合同；<br />FILLING（填参中），SIGNING（签署中）：撤回合同；<br />COMPLETE（已完成）：发起作废、并签署发起方。 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// “签署中”状态下撤回合同
ContractInvalidRequest request = new ContractInvalidRequest(2589413512234848660L, "撤回合同");
String response = sdkClient.service(request);
SdkResponse responseObj = JSONUtils.toQysResponse(response);
if(responseObj.getCode() == 0) {
	logger.info("合同撤回成功");
} else {
	logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
// “已完成”状态下请求作废合同，同时发起方签署作废合同
//		ContractInvalidRequest request = new ContractInvalidRequest(2589382258757710050L, null, "请求作废合同", false);
//		String response = sdkClient.service(request);
//		SdkResponse responseObj = JSONUtils.toQysResponse(response);
//		if(responseObj.getCode() == 0) {
//			logger.info("合同作废请求成功");
//		} else {
//			logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
//		}
// “草稿”状态下删除合同
//		ContractInvalidRequest request = new ContractInvalidRequest(2589382258757710050L);
//		String response = sdkClient.service(request);
//		SdkResponse responseObj = JSONUtils.toQysResponse(response);
//		if(responseObj.getCode() == 0) {
//			logger.info("合同删除请求成功");
//		} else {
//			logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
//		}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

# 2 印章接口

## 2.1 印章列表

**请求地址：**/v2/seal/list

**请求方法：**GET

**请求参数：**

| 参数         | 类型    | 必须 | 描述                   |
| ------------ | ------- | ---- | ---------------------- |
| selectOffset | Integer | 否   | 查询起始位置，默认为0  |
| selectLimit  | Integer | 否   | 查询列表大小，默认1000 |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |
| result  | Result  | 印章数据 |

Result（印章数据）：

| 参数       | 类型        | 描述     |
| ---------- | ----------- | -------- |
| totalCount | Integer     | 印章数量 |
| list       | Array[Seal] | 印章列表 |

Seal（印章数据）：

| 参数       | 类型   | 描述                                  |
| ---------- | ------ | ------------------------------------- |
| id         | String | 印章ID                                |
| name       | String | 印章名称                              |
| createTime | String | 创建时间，格式为：yyyy-MM-dd HH:mm:ss |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 印章列表查询
SealListRequest request = new SealListRequest();
String response = sdkClient.service(request);
SdkResponse<SealListResult> responseObj = JSONUtils.toQysResponse(response, SealListResult.class);
if(responseObj.getCode() == 0) {
	SealListResult sealList = responseObj.getResult();
	logger.info("印章列表查询，数量:{}", sealList.getTotalCount());
} else {
	logger.info("印章列表失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 2.2 印章图片

**请求地址：**/v2/seal/image

**请求方法：**GET

**请求参数：**

| 参数   | 类型   | 必须 | 描述   |
| ------ | ------ | ---- | ------ |
| sealId | String | 否   | 印章ID |

**返回参数：**

印章图片流

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 印章图片下载
SealImageRequest request = new SealImageRequest(2391453670764376075L);
FileOutputStream fos = new FileOutputStream("E:/test/seal.png");
sdkClient.download(request, fos);
fos.close();
logger.info("下载印章成功");
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

# 3 业务分类

## 3.1 业务分类列表

**请求地址：**/v2/category/list

**请求方法：**GET

**请求参数：**

| 参数         | 类型    | 必须 | 描述                   |
| ------------ | ------- | ---- | ---------------------- |
| selectOffset | Integer | 否   | 查询起始位置，默认为0  |
| selectLimit  | Integer | 否   | 查询列表大小，默认1000 |

**返回参数：**

| 参数    | 类型    | 描述         |
| ------- | ------- | ------------ |
| code    | Integer | 响应码       |
| message | String  | 响应消息     |
| result  | Result  | 业务分类数据 |

Result（业务分类数据）：

| 参数       | 类型            | 描述         |
| ---------- | --------------- | ------------ |
| totalCount | Integer         | 业务分类数量 |
| list       | Array[Category] | 业务分类列表 |

Category（业务分类）：

| 参数       | 类型            | 描述                                  |
| ---------- | --------------- | ------------------------------------- |
| id         | String          | 业务分类ID                            |
| name       | Array[Category] | 业务分类名称                          |
| createTime | String          | 创建时间，格式为：yyyy-MM-dd HH:mm:ss |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 业务分类列表
CategoryListRequest request = new CategoryListRequest();
String response = sdkClient.service(request);
SdkResponse<CategoryListResult> responseObj = JSONUtils.toQysResponse(response, CategoryListResult.class);
if(responseObj.getCode() == 0) {
	CategoryListResult categoryList = responseObj.getResult();
	logger.info("业务分类列表查询，数量:{}", categoryList.getTotalCount());
} else {
	logger.info("业务分类列表失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

# 4 文件模板

## 4.1 模板列表

**请求地址：**/v2/template/list

**请求方法：**GET

**请求参数：**

| 参数         | 类型    | 必须 | 描述                   |
| ------------ | ------- | ---- | ---------------------- |
| selectOffset | Integer | 否   | 查询起始位置，默认为0  |
| selectLimit  | Integer | 否   | 查询列表大小，默认1000 |

**返回参数：**

| 参数    | 类型    | 描述         |
| ------- | ------- | ------------ |
| code    | Integer | 响应码       |
| message | String  | 响应消息     |
| result  | Result  | 文件模板数据 |

Result（文件模板数据）：

| 参数       | 类型            | 描述     |
| ---------- | --------------- | -------- |
| totalCount | Integer         | 模板数量 |
| list       | Array[Template] | 模板列表 |

Template（文件模板）：

| 参数       | 类型                 | 描述                                  |
| ---------- | -------------------- | ------------------------------------- |
| id         | String               | 模板ID                                |
| name       | 业务分类名称         | 模板名称                              |
| createTime | String               | 创建时间，格式为：yyyy-MM-dd HH:mm:ss |
| parameters | Array[TemplateParam] | 模板参数                              |

TemplateParam（模板参数）：

| 参数     | 类型    | 描述                                                         |
| -------- | ------- | ------------------------------------------------------------ |
| name     | String  | 参数名称                                                     |
| required | Boolean | 是否必填                                                     |
| type     | String  | 参数类型：text(文本),	textarea(多行文本),	<br />date(时间),	person(身份证号),	radio(单选),<br />	checkbox(多选),	seal(签章),	signature(签名) |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 模板列表
TemplateListRequest request = new TemplateListRequest();
String response = sdkClient.service(request);
SdkResponse<TemplateListResult> responseObj = JSONUtils.toQysResponse(response, TemplateListResult.class);
if(responseObj.getCode() == 0) {
	TemplateListResult templateList = responseObj.getResult();
	logger.info("文件模板列表查询，数量:{}", templateList.getTotalCount());
} else {
	logger.info("文件模板列表失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 4.2 模板预览页面

**描述：**待实现。

**请求地址：**/v2/template/pageurl

**请求方法：**GET

**请求参数：**

| 参数       | 类型   | 必须 | 描述                  |
| ---------- | ------ | ---- | --------------------- |
| templateId | String | 是   | 查询起始位置，默认为0 |

**返回参数：**

| 参数    | 类型    | 描述         |
| ------- | ------- | ------------ |
| code    | Integer | 响应码       |
| message | String  | 响应消息     |
| result  | Result  | 模板页面数据 |

Result（模板页面数据）：

| 参数    | 类型   | 描述         |
| ------- | ------ | ------------ |
| pageUrl | String | 模板预览页面 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 模板预览页面
TemplatePageRequest request = new TemplatePageRequest(2586924492742590568L);
String response = sdkClient.service(request);
SdkResponse<TemplatePageResult> responseObj = JSONUtils.toQysResponse(response, TemplatePageResult.class);
if(responseObj.getCode() == 0) {
	TemplatePageResult result = responseObj.getResult();
	logger.info("模板页面查询，页面链接:{}", result.getPageUrl());
} else {
	logger.info("模板页面查询失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

# 5 公司接口

## 5.1 公司信息

**请求地址：**/v2/company/detail

**请求方法：**GET

**请求参数：**

| 参数        | 类型   | 必须 | 描述     |
| ----------- | ------ | ---- | -------- |
| companyName | String | 是   | 公司名称 |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |
| result  | Result  | 公司数据 |

Result（模板页面数据）：

| 参数       | 类型   | 描述                                                         |
| ---------- | ------ | ------------------------------------------------------------ |
| status     | String | 公司状态：UNREGISTERED（未注册），<br />CERTIFYING（认证中），AUTH_SUCCESS（认证成功） |
| id         | String | 公司ID                                                       |
| name       | String | 公司名称                                                     |
| registerNo | String | 公司代码（工商注册号/统一社会信用代码）                      |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 公司信息
CompanyDetailRequest request = new CompanyDetailRequest("测试公司");
String response = sdkClient.service(request);
SdkResponse<Company> responseObj = JSONUtils.toQysResponse(response, Company.class);
if(responseObj.getCode() == 0) {
	Company result = responseObj.getResult();
	logger.info("公司查询，状态:{}", result.getStatus());
} else {
	logger.info("公司查询失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

## 5.2 员工接口

### 5.2.1 员工列表

**请求地址：**/v2/employee/list

**请求方法：**GET

**请求参数：**

| 参数         | 类型    | 必须 | 描述                   |
| ------------ | ------- | ---- | ---------------------- |
| selectOffset | Integer | 否   | 查询起始位置，默认为0  |
| selectLimit  | Integer | 否   | 查询列表大小，默认1000 |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |
| result  | Result  | 员工数据 |

Result（员工数据）：

| 参数       | 类型            | 描述     |
| ---------- | --------------- | -------- |
| totalCount | Integer         | 员工数量 |
| list       | Array[Employee] | 员工列表 |

Employee（员工信息）：

| 参数       | 类型   | 描述     |
| ---------- | ------ | -------- |
| id         | String | 员工ID   |
| name       | String | 员工姓名 |
| idCardNo   | String | 身份证号 |
| mobile     | String | 手机号   |
| email      | String | 邮箱     |
| number     | String | 员工编号 |
| createTime | String | 创建时间 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 员工列表
EmployeeListRequest request = new EmployeeListRequest();
String response = sdkClient.service(request);
SdkResponse<EmployeeListResult> responseObj = JSONUtils.toQysResponse(response, EmployeeListResult.class);
if(responseObj.getCode() == 0) {
	EmployeeListResult result = responseObj.getResult();
	logger.info("员工查询，员工数量：{}", result.getTotalCount());
} else {
	logger.info("员工查询失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 5.2.2 添加员工

**请求地址：**/v2/employee/create

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数   | 类型   | 必须 | 描述                       |
| ------ | ------ | ---- | -------------------------- |
| user   | User   | 是   | 员工信息，用于确定员工身份 |
| number | String | 否   | 员工编号                   |

User（个人用户）：

| 参数        | 类型   | 必须 | 描述                                                         |
| ----------- | ------ | ---- | ------------------------------------------------------------ |
| name        | String | 是   | 姓名                                                         |
| contact     | String | 是   | 联系方式                                                     |
| contactType | String | 是   | 联系类型：MOBILE（手机号），EMAIL（邮箱），EMPLOYEEID（员工ID），NUMBER（员工编号） |

**返回参数：**

| 参数    | 类型     | 描述     |
| ------- | -------- | -------- |
| code    | Integer  | 响应码   |
| message | String   | 响应消息 |
| result  | Employee | 员工数据 |

Employee（员工信息）：

| 参数       | 类型   | 描述     |
| ---------- | ------ | -------- |
| id         | String | 员工ID   |
| name       | String | 员工姓名 |
| idCardNo   | String | 身份证号 |
| mobile     | String | 手机号   |
| email      | String | 邮箱     |
| number     | String | 员工编号 |
| createTime | String | 创建时间 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1703   | EMPLOYEE NAME NOT MATCH，员工的姓名和实际姓名不匹配；<br />员工在契约锁已认证时，传入的员工姓名必须与真实的员工姓名一致。 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 添加员工
User user = new User("宋三", "13636350224", "MOBILE");
EmployeeCreateRequest request = new EmployeeCreateRequest(user, "123127");
String response = sdkClient.service(request);
SdkResponse<Employee> responseObj = JSONUtils.toQysResponse(response, Employee.class);
if(responseObj.getCode() == 0) {
	Employee result = responseObj.getResult();
	logger.info("创建员工，员工ID：{}", result.getId());
} else {
	logger.info("创建员工失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 5.2.3 更新员工

**描述：**支持修改员工姓名和员工编号。

**请求地址：**/v2/employee/update

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数   | 类型   | 必须 | 描述                       |
| ------ | ------ | ---- | -------------------------- |
| user   | User   | 是   | 员工信息，用于确定员工身份 |
| number | String | 否   | 员工编号                   |

User（个人用户）：

| 参数        | 类型   | 必须 | 描述                                                         |
| ----------- | ------ | ---- | ------------------------------------------------------------ |
| name        | String | 否   | 姓名                                                         |
| contact     | String | 是   | 联系方式                                                     |
| contactType | String | 是   | 联系类型：MOBILE（手机号），EMAIL（邮箱），EMPLOYEEID（员工ID），NUMBER（员工编号） |

**返回参数：**

| 参数    | 类型    | 描述     |
| ------- | ------- | -------- |
| code    | Integer | 响应码   |
| message | String  | 响应消息 |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| 1701   | EMPLOYEE NOT FOUND，找不到员工                               |
| 1703   | EMPLOYEE NAME NOT MATCH，员工的姓名和实际姓名不匹配；<br />员工在契约锁已认证时，传入的员工姓名必须与真实的员工姓名一致。 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 更新员工
User user = new User("宋三", "13636350222", "MOBILE");
EmployeeUpdateRequest request = new EmployeeUpdateRequest(user, "123126");
String response = sdkClient.service(request);
SdkResponse<Employee> responseObj = JSONUtils.toQysResponse(response, Employee.class);
if(responseObj.getCode() == 0) {
	logger.info("更新员工成功");
} else {
	logger.info("更新失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

### 5.2.4 移除员工

**请求地址：**/v2/employee/remove

**请求方法：**POST

**请求格式:** application/json;charset=UTF-8

**请求参数：**

| 参数        | 类型   | 必须 | 描述                                                         |
| ----------- | ------ | ---- | ------------------------------------------------------------ |
| contact     | String | 是   | 联系方式                                                     |
| contactType | String | 是   | 联系类型：MOBILE（手机号），<br />EMAIL（邮箱），EMPLOYEEID（员工ID） |

**返回参数：**

| 参数    | 类型    | 描述            |
| ------- | ------- | --------------- |
| code    | Integer | 响应码，0为成功 |
| message | String  | 响应消息        |

**响应码（全局响应码请查看文档末“全局响应码”）：**

| 响应码 | 描述                           |
| ------ | ------------------------------ |
| 1701   | EMPLOYEE NOT FOUND，找不到员工 |
| 1702   | EMPLOYEE DISMISSED，员工已移除 |

**请求示例：**

<details>
<summary>html示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Java示例</summary>
<pre><code>
// 初始化sdkClient
String serverUrl = "http://openapi.qiyuesuo.net";
String accessKey = "7EswyQzhBe";
String accessSecret = "D0Wisceb8FZzV7grDBzcInQdWlzKLY";
SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
// 移除员工
User user = new User("宋三", "13636350224", "MOBILE");
EmployeeRemoveRequest request = new EmployeeRemoveRequest(user);
String response = sdkClient.service(request);
SdkResponse<Employee> responseObj = JSONUtils.toQysResponse(response, Employee.class);
if(responseObj.getCode() == 0) {
	logger.info("移除员工成功");
} else {
	logger.info("移除失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
}
</code></pre>
</details>

<details>
<summary>C#示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>PHP示例</summary>
<pre><code>
</code></pre>
</details>

<details>
<summary>Python示例</summary>
<pre><code>
</code></pre>
</details>

# 全局响应码

| 响应码 | 描述                                |
| ------ | ----------------------------------- |
| 0      | SUCCESS，接口调用成功               |
| 1001   | ERROR，服务器内部错误               |
| 1002   | PERMISSION DENIED，没有接口调用权限 |
| 1005   | INVALID PARAM，无效的参数           |
| 1601   | INSUFFICIENT BALANCE，余额不足      |
