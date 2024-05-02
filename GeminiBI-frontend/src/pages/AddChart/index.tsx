import { useModel } from '@umijs/max';
import React, { useState } from 'react';
import { createStyles } from 'antd-style';
import {Button, Card, Col, Divider, Form, Input, message, Row, Select, Space, Spin, Upload} from "antd";
import TextArea from "antd/es/input/TextArea";
import {UploadOutlined} from "@ant-design/icons";
import {genChartUsingPost} from "@/services/Gemini-BI/chartController";
import ReactECharts from 'echarts-for-react';

const useStyles = createStyles(({ token }) => {
  return {
    action: {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    },
    lang: {
      width: 42,
      height: 42,
      lineHeight: '42px',
      position: 'fixed',
      right: 16,
      borderRadius: token.borderRadius,
      ':hover': {
        backgroundColor: token.colorBgTextHover,
      },
    },
    container: {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage:
        "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
      backgroundSize: '100% 100%',
    },
  };
});

const normFile = (e: any) => {
  console.log('Upload event:', e);
  if (Array.isArray(e)) {
    return e;
  }
  return e?.fileList;
};

const AddChart: React.FC = () => {


  const [chart, setChart] = useState<API.BIResponse>();

  const [type, setType] = useState<string>('account');
  const { initialState, setInitialState } = useModel('@@initialState');
  const { styles } = useStyles();
  const [ submitting, setSubmitting] = useState<boolean>(false);
  const [option, setOption] = useState<any>();
  const onFinish = async (values: any) => {
    if (submitting) {
      return;
    }
    setSubmitting(true);
    const params = {
      ...values,
      file: undefined
    }
    try {
      const res = await genChartUsingPost(params, {}, values.file[0]?.originFileObj);
      // console.log(res);
      if(!res?.data) {
        message.error('analysis fail');
      } else {
        console.log('hello')
        const data = res?.data?.chartData.replaceAll("```json", "")
          .replaceAll("```", "")
          .replaceAll("\\n", "\n")
          .replaceAll("'", "\"")
          // .replace("\\\"", "\"")
        console.log(data)
        const chartOption = JSON.parse(data ?? "");
        console.log(chartOption);
        if (!chartOption) {
          console.log("fail to generate chart");
          throw new Error('Chart Fail to analysis');
        } else {
          console.log(chartOption)
          setChart(res?.data);
          setOption(chartOption);
          message.success("analysis success");
        }
      }

    } catch (e: any) {
      message.error('analysis fail');
      console.error(e)
    }
    setSubmitting(false);

  };


  return (
    <div className='addChart'>
      <Row gutter = {24}>
        <Col span={12}>
          <Card title = "Gemini Analysis">
            <Form
              name="addChart"
              onFinish={onFinish}
              initialValues={{
              }}
              labelAlign="left"
              wrapperCol={{span:16}}
              labelCol={{span:4}}
            >

              <Form.Item name="goal" label="Analysis Goal" rules={[{ required: true, message: 'Please select your analysis goal!' }]}>
                <TextArea placeholder="Please input your analysis goal. Example: I want to analysis user trend of my website."/>
              </Form.Item>

              <Form.Item name="chartName" label="Chart Name">
                <Input placeholder="Please input your chart name."/>
              </Form.Item>


              <Form.Item
                name="chartType"
                label="ChartType"
                hasFeedback
                rules={[{ required: true, message: 'Please select your chart type!' }]}
              >
                <Select placeholder="Please select a country">
                  <Option value="series-line">Line</Option>
                  <Option value="series-pie">Pie</Option>
                  <Option value="series-bar">Bar</Option>
                  <Option value="series-heatmap">Heat Map</Option>
                  <Option value="series-boxplot">Box Plot</Option>
                  <Option value="series-candlestick">Candle Stick</Option>
                </Select>
              </Form.Item>

              <Form.Item
                name="file"
                label="Upload"
                valuePropName="fileList"
                getValueFromEvent={normFile}
              >
                <Upload name="logo" >
                  <Button icon={<UploadOutlined />}>Click to upload your csv file</Button>
                </Upload>
              </Form.Item>

              <Form.Item wrapperCol={{ span: 12, offset: 4 }}>
                <Space>
                  <Button type="primary" htmlType="submit" loading={submitting} disabled={submitting} >
                    Analysis
                  </Button>
                  <Button htmlType="reset">reset</Button>
                </Space>
              </Form.Item>
            </Form>
          </Card>
        </Col>
        <Col span={12}>
          <Card title = "Analysis Result">
             {chart?.analysisData?.replaceAll("\\n", "")}
             <Spin spinning = {submitting} />
          </Card>
          <Divider></Divider>
          <Card title = "Data Visualization ">
              {option && <ReactECharts option={option}/>}
              <Spin spinning = {submitting} />

          </Card>

        </Col>

      </Row>


    </div>
  );
};
export default AddChart;
