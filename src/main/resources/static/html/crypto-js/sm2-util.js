
function doGenerate() {
	var curve = "sm2";
	var ec = new KJUR.crypto.ECDSA({
		"curve" : curve
	});
	var keypair = ec.generateKeyPairHex();
	var json={
			privateKey: keypair.ecprvhex,
			publicKey: keypair.ecpubhex
	};
	console.log(json);
	return json;
}

function sm2_cryptbymode(cipherMode, pubkeyHex, msg) {
	var msgData = CryptoJS.enc.Utf8.parse(msg);
	var cipher = new SM2Cipher(cipherMode);
	var userKey = cipher.CreatePoint(pubkeyHex);
	msgData = cipher.str2Bytes(msgData.toString());
	return cipher.Encrypt(userKey, msgData);
}
//java端默认使用C1C2C3模式，cipherMode=0
function sm2_crypt(pubkeyHex, msg) {
	return sm2_cryptbymode(0, pubkeyHex, msg);
}

function sm2_decryptbymode(cipherMode, prvkeyHex, encryptData) {
	var privateKey = new BigInteger(prvkeyHex, 16);
	var cipher = new SM2Cipher(cipherMode);
	return cipher.Decrypt(privateKey, encryptData);
}
//java端默认使用C1C2C3模式，cipherMode=0
function sm2_decrypt(prvkeyHex, encryptData) {
	return sm2_decryptbymode(0, prvkeyHex, encryptData);
}

//SM3摘要算法
function digest(src) {
	var msgData = CryptoJS.enc.Utf8.parse(src);
	var sm3keycur = new SM3Digest();
	msgData = sm3keycur.GetWords(msgData.toString());
	sm3keycur.BlockUpdate(msgData, 0, msgData.length);
	var c3 = new Array(32);
	sm3keycur.DoFinal(c3, 0);
	return sm3keycur.GetHex(c3).toString().toUpperCase();
}

var curve = 'sm2';
var algorithm = 'SM3withSM2';


//js端的签名算法有问题，无法和java端配合，考虑到客户端一般不做签名，所以暂时只提供sm3-sm2的签名验证
function doVerifyByPublicKey(sign, pubkey, msg) {
	/*var sig = new KJUR.crypto.Signature({
		"alg" : algorithm,
		"prov" : "cryptojs/jsrsa"
	});
	sig.initVerifyByPublicKey({
		'ecpubhex' : pubkey,
		'eccurvename' : curve
	});
	sig.updateString(msg);
	return sig.verify(sign);*/
	return sm2.doVerifySignature(msg, sign, pubkey,{
		    der: true,hash: true
	}); // 验签结果
}

function doSignByPrivateKey(prikeyHex, msg) {
	/*var sig = new KJUR.crypto.Signature({
		"alg" : algorithm,
		"prov" : "cryptojs/jsrsa"
	});
	sig.initSign({
		'ecprvhex' : prikeyHex,
		'eccurvename' : curve
	});
	sig.updateString(msg);
	return sig.sign();*/
	return sm2.doSignature(msg, prikeyHex, {
	    der: true, hash: true
	}); // 签名
}


function test() {
	var publicKeyHex = "04FE21D81EE068C52903A4763F9123043D730BE18FE211A25B685CB7F7D0F3E113BBA2D13E50E18389FDF19C56B4DA1EEBFC8C699AF3FA83790F7257A4D84A4D48";
	var privateKeyHex = "00C4854BE8098E606AB530CCA09D42C6C897F22355339920C955669B6C1A049994";
	var signHex = doSignByPrivateKey(privateKeyHex, 'bd-123456');
	console.log(signHex);
	console.log(doVerifyByPublicKey(signHex, publicKeyHex, 'bd-123456'));
}