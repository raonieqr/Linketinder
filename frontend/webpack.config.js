const path = require('path')
const CopyPlugin = require('copy-webpack-plugin')
module.exports = {
    mode: 'production',
    entry: {
        candidate: './candidate/candidate.ts',
        business: './business/company.ts',
    },
    devServer: {
        contentBase: path.resolve(__dirname, 'dist'),
        compress: true,
        port: 9000,
    },
    output: {
        filename: '[name].min.js',
        path: path.join(__dirname, 'dist')
    },
    plugins: [
        new CopyPlugin({
            patterns:[
            {from: 'candidate', to: 'candidate'},
            {from: 'business', to: 'business'}],
        }),
    ],
    resolve: {
       extensions: ['.ts', '.js'] 
    },
    module: {
        rules: [{
            test: /\.ts$/,
            use: 'ts-loader',
            exclude: /node_modules/
        }]
    }
}