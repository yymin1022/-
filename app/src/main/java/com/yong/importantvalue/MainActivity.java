package com.yong.importantvalue;

import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	//NullPointerException을 방지하기 위해 모든 int, float 변수를 0으로 초기화
	float result = 0;
	float inputSon = 0;
	float inputMother = 0;
	boolean isAbsolute = true; //구하고자 하는 값이 절댓값인지 상댓값인지 판단하는 boolean 값
	boolean isError = false; //에러가 났는지를 확인하는 boolean
	
	View view;
	EditText son;
	EditText mother;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	
	public void openDialog(final int menu){
		//EditText를 가 추가된 Layout XML을 불러와 Dialog의 View로 자정
		LayoutInflater inflater = getLayoutInflater();
		view = inflater.inflate(R.layout.dialog_layout, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		//분자와 분모 EditText 변수 초기화
		son = view.findViewById(R.id.inputSon);
		mother = view.findViewById(R.id.inputMother);
		switch(menu){
			case 1:
				builder.setTitle("절대밀도 구하기");
				son.setHint("종의 개체 수");
				mother.setHint("방형구의 전체 면적");
				isAbsolute = true;
				break;
			case 2:
				builder.setTitle("절대빈도 구하기");
				son.setHint("종이 나타난 방형구 수");
				mother.setHint("방형구의 전체 수");
				isAbsolute = true;
				break;
			case 3:
				builder.setTitle("절대피도 구하기");
				son.setHint("종이 차지하는 면적");
				mother.setHint("방형구의 전체 면적");
				isAbsolute = true;
				break;
			case 4:
				builder.setTitle("상대밀도 구하기");
				son.setHint("종의 절대밀도");
				mother.setHint("총 절대밀도의 합");
				isAbsolute = false;
				break;
			case 5:
				builder.setTitle("상대빈도 구하기");
				son.setHint("종의 절대빈도");
				mother.setHint("총 절대빈도의 합");
				isAbsolute = false;
				break;
			case 6:
				builder.setTitle("상대피도 구하기");
				son.setHint("종의 절대피도");
				mother.setHint("총 절대피도의 합");
				isAbsolute = false;
				break;
		}
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface p1, int p2)
			{
				try{
					inputSon = Float.valueOf(son.getText().toString());
				}catch(NumberFormatException e){
					//정수/실수가 아닌 경우 에러 표시
					isError = true;
				}
				try{
					inputMother = Float.valueOf(mother.getText().toString());
				}catch(NumberFormatException e){
					//정수/실수가 아닌 경우 에러 표시
					isError = true;
				}
				if(isAbsolute){
					//절댓값을 구하는 경우
					result = inputSon/inputMother;
				}else{
					//상댓값을 구하는 경우
					result = (inputSon/inputMother) * 100;
				}
				if(Float.isNaN(result)){
					//계산 결과가 숫자가 아닐 경우 필터
					isError = true;
				}
				if(!isError){
					//애러가 없는 경우 계산 결과를 Dialog로 표시시
					AlertDialog.Builder resultDialog = new AlertDialog.Builder(MainActivity.this);
					resultDialog.setTitle("계산 결과");
					switch(menu){
						case 1:
							resultDialog.setTitle("절대밀도 : " + String.valueOf(result));
							break;
						case 2:
							resultDialog.setTitle("절대빈도 : " + String.valueOf(result));
							break;
						case 3:
							resultDialog.setTitle("절대피도 : " + String.valueOf(result));
							break;
						case 4:
							resultDialog.setTitle("상대밀도 : " + String.valueOf(result));
							break;
						case 5:
							resultDialog.setTitle("상대빈도 : " + String.valueOf(result));
							break;
						case 6:
							resultDialog.setTitle("상대피도 : " + String.valueOf(result));
							break;
					}
					resultDialog.setPositiveButton("확인", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int p2)
						{
							dialog.dismiss();
						}
					});
					resultDialog.show();
				}else{
					//에러가 있는 경우 오류메시지 표시
					Toast.makeText(getApplicationContext(), "오류가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
					p1.dismiss();
					isError = false;
				}
			p1.dismiss();
			}
		});
		builder.show();
	}
	
	public void abDensity(View v){
		openDialog(1);
	}
	
	public void abFrequency(View v){
		openDialog(2);
	}
	
	public void abValue(View v){
		openDialog(3);
	}
	
	public void reDensity(View v){
		openDialog(4);
	}

	public void reFrequency(View v){
		openDialog(5);
	}

	public void reValue(View v){
		openDialog(6);
	}
}
